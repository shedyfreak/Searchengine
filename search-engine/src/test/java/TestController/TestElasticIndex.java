package TestController;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.core.type.TypeReference;
import bfh.ch.controller.ElasticIndex;
import bfh.ch.controller.ElasticIndex.Payload;
import bfh.ch.entities.Job;

class TestElasticIndex {
    public final ElasticIndex<Job> index = new ElasticIndex<>("http://localhost:9200/total_dataset",
            new TypeReference<>() {
            });
    public final Payload p = new ElasticIndex.PayloadBuilder().
            addBoolMustWildcard("field", "value").build();

    @Test
    void testElasticIndex() {
        assertNotNull(index);

    }

    @Test
    void testSearchOne() {
        assertNotNull(index.search(new ElasticIndex.PayloadBuilder().build()));
    }

    @Test
    void testSearchTwo() {
        assertNotEquals(index.search(new ElasticIndex.PayloadBuilder().build()).size(), -1);
    }

    @Test
    void testSearchThree() {
        assertNotNull(index.search(new ElasticIndex.PayloadBuilder().build()).get(0));
    }

    @Test
    void TestPayload() {
        assertNotNull(new ElasticIndex.Payload());
    }

    @Test
    void TestPayloadBuilder() {
        assertNotNull(new ElasticIndex.PayloadBuilder().build());
    }

    @Test
    void TestPayloadContent() {
        assertTrue(p.containsKey("query"));
    }
}
