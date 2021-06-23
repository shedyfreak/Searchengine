package bfh.ch.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * backend class for the ElacticIndex instance.
 *
 * @param <T> Generic Type for Jackson mapping
 * @author shedy and ghofrane
 * @version 2.0
 */
public class ElasticIndex<T> {
    private final String url;
    private final TypeReference<Result<T>> type;
    private static final int SUCCESS = 400;

    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofMinutes(2))
            .build();

    /**
     * <p>
     * This class is the backbone of query building.
     * this class is used for building the payload for the elasticsearch query
     * As Hashmaps behave like JSON objects ( key , value ) we will extend it
     * use it's powerful methods to emulate that behaviour
     * </p>
     *
     * @author shedy and ghofrane
     * @version 1.0
     */
    public static class Payload extends HashMap<String, Object> {

        private static final long serialVersionUID = 1L;

        /**
         * use this method to create a key/value pair and value as another object
         * simulating JSON objects
         *
         * @param key  emulate key in json objects
         * @return the created payload that will emulate value in a json object
         */
        public Payload path(String key) {
            return (Payload) computeIfAbsent(key, i -> new Payload());
        }

        /**
         * use this method to create a list of objects mapped by key value logic.
         *
         * @param key wanted key
         * @return the created payload will emulate a list of json objects
         */
        public Payload add(String key) {
            Payload item = new Payload();
            ((ArrayList) computeIfAbsent(key, i -> new ArrayList<>())).add(item);
            return item;
        }

        /**
         * method used to set a key with its value.
         *
         * @param key   wanted key
         * @param value respectful value
         * @return the payload with its new key and value
         */
        public Payload set(String key, Object value) {
            this.put(key, value);
            return this;
        }
    }

    /**
     * <p> this is an added Class to build specific queries
     * Following Elasticsearch DSL.
     * </p>
     *
     * @version 1.0
     */
    public static class PayloadBuilder {
        private final Payload payload = new Payload();

        /**
         * method used for retrieving the data filtered by location/language/description.
         *
         * @param field JSON Key (can be language, location or description)
         * @param value JSON value (the value looking for that key)
         * @return Payload containing embedded Payload Objects
         * to add boolean condition containing wildcards for certain field, value
         */
        public PayloadBuilder addBoolMustWildcard(String field, String value) {
            payload.path("query").path("bool")
                    .add("must").path("wildcard")
                    .set(field, value);
            return this;
        }

        /**
         * method used for adding date range search criteria in JSON body.
         *
         * @param field JSON field range ( ElasticDSL)
         * @param low   value for the lowest range ( date in our usecase)
         * @param high  value for the upper band
         * @return payloadBuilder object containing JSON body as payload object
         */
        public PayloadBuilder addBoolMustInclusiveRange(String field, Object low, Object high) {
            payload.path("query").path("bool")
                    .add("must").path("range").path(field)
                    .set("gte", low).set("lte", high);
            return this;
        }

        /**
         * Getter for payload object.
         *
         * @return Object payload
         */
        public Payload build() {
            return payload;
        }
    }

    /**
     * based on the data format this class represents the main result object.
     *
     * @param <T> Generic type for Jackson mapping
     * @author shedy and ghofrane
     */
    public static class Result<T> {
        public ResultHits<T> hits;
    }

    /**
     * based on the data format this class represents a list of the hits.
     *
     * @param <T> Generic type for Jackson mapping
     * @author shedy and ghofrane
     */
    public static class ResultHits<T> {
        public List<ResultHit<T>> hits;
    }

    /**
     * based on the data format this class represents the hit.
     *
     * @param <T> Generic type for Jackson mapping _source object contains
     *            each job data
     * @author shedy
     */
    public static class ResultHit<T> {
        public T _source;
    }

    /**
     * constructor of the ElasticIndex class.
     *
     * @param url  url to fetch
     * @param type Type object for jackson mapping
     */
    public ElasticIndex(final String url, TypeReference<Result<T>> type) {
        this.url = url;
        this.type = type;
    }

    /**
     * main method of the class, generates the search.
     *
     * @param payload payload containing query-like objects
     * @return List of filtered jobs
     */
    public List<T> search(final Payload payload) {
        try {
            URI uri = new URI(url + "/_search");
            String body = mapper.writeValueAsString(payload);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() < SUCCESS) {
                return mapper.readValue(response.body(), type).hits.hits.stream()
                        .map(hit -> hit._source)
                        .collect(Collectors.toList());
            }
            throw new RuntimeException("Invalid json response");
        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
