package bfh.ch.controller;

import bfh.ch.entities.Job;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

/**
 * class for the detailed view of the job offer
 */
public class ViewController implements Initializable {
    @FXML
    public AnchorPane main;
    @FXML
    public ImageView logo;
    @FXML
    public Button findJobsButton;
    @FXML
    public TextField searchInput;
    @FXML
    public ChoiceBox location;
    @FXML
    public ChoiceBox language;
    @FXML
    public DatePicker datePicker;
    @FXML
    public ListView<Job> jobList;
    @FXML
    public Button detailedButton;
    @FXML
    public ImageView refreshButton;
    static final int V1DIMENSION = 810;
    static final int V2DIMENSION = 680;
    public ObservableList observableList = FXCollections.observableArrayList();
    final List<String> languages = Arrays.asList("EN", "DE", "IT", "FR");
    final List<String> locations = Arrays.asList("ZH", "BE", "LU", "UR", "SZ", "OW", "NW", "GL", "ZG", "FR", "SO", "BS",
            "BL", "SH", "AR", "AI", "SG", "GR", "AG", "TG", "TI", "VD", "VS", "NE", "GE", "JU");
    public final ElasticIndex<Job> index = new ElasticIndex<>("http://localhost:9200/total_dataset",
            new TypeReference<>() {
            });

    /**
     * handler for finding job based on input keyword, chosen location, language and date
     * after selecting the filters and clicking on the find jobs button the displayed jobs
     * gets filtered and narrowed down to the user's likings
     *
     * @param event choosing the filter and/or entering a keyword
     */
    @FXML
    public void handleFindJobsAction(ActionEvent event) {

        String searchKeyword = searchInput.getText();
        LocalDate date = datePicker.getValue();
        LocalDate currentDate = LocalDate.now();
        String chosenDate = date != null
                ? date.toString() : null;
        String lang = language.getValue().equals("language")
                ? "*" : language.getValue().toString();
        String loc = location.getValue().equals("location")
                ? "*" : "*" + location.getValue().toString() + "*";

        List<Job> jobs = index
                .search(new ElasticIndex.PayloadBuilder().addBoolMustWildcard("title",
                        "*" + searchKeyword + "*")
                        .addBoolMustWildcard("description",
                                "*" + searchKeyword + "*")
                        .addBoolMustWildcard("language", lang)
                        .addBoolMustWildcard("location", loc)
                        .addBoolMustInclusiveRange("date", chosenDate, currentDate.toString())
                        .build());
        observableList.setAll(jobs);
        jobList.setItems(observableList);
    }

    /**
     * this method disables the button view detailed offer
     * the view detailed offer button only gets clickable when the user selects an offer
     *
     * @param event when choosing job offer
     */
    @FXML
    public void handleSelectRow(MouseEvent event) {
        this.detailedButton.setDisable(false);
    }

    /**
     * showing the details of chosen job
     * once the view detailed offer button is clicked the user the view changes
     * to display the jb offer's details
     *
     * @param event selecting a job offer and pushing the button
     * @throws IOException
     */
    @FXML
    public void handleDetailsAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass()
                .getResource("/view/DetailedJobView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, V1DIMENSION, V2DIMENSION);
        DetailedViewController detailedController = loader.getController();
        detailedController.showOffer(jobList.getSelectionModel().getSelectedItem());
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * handler for refresher button
     * gets the main view into its starting state
     *
     * @param event click on refresher button
     */
    @FXML
    public void refreshMainView(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(
                getClass()
                        .getResource("/view/MainView.fxml"));
        Scene scene = new Scene(root, V1DIMENSION, V2DIMENSION);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * method for initializing the main view
     * by defaults the jobs are displayed randomly in the main view
     * using a general query from elasticsearch
     *
     * @param url needed to be able to override the method
     * @param rb needed to be able to override the method
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.logo = new ImageView("file:logo.jpg");
        this.refreshButton = new ImageView("file:refresh.png");
        List<Job> jobs = index.search(new ElasticIndex.PayloadBuilder()
                .addBoolMustWildcard("title", "*")
                .addBoolMustWildcard("language", "*")
                .addBoolMustWildcard("location", "*")
                .build());
        observableList.setAll(jobs);
        jobList.setItems(observableList);
        location.getItems().addAll(locations);
        language.getItems().addAll(languages);
        this.detailedButton.setDisable(true);

    }
}
