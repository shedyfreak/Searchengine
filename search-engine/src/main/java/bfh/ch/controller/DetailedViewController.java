package bfh.ch.controller;

import bfh.ch.entities.Job;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Class To Control the first view : Job description View.
 */
public class DetailedViewController extends Application {
    @FXML
    public AnchorPane anchor;
    @FXML
    public Label jobTitle;
    @FXML
    public Label dateLabel;
    @FXML
    public TextFlow description;
    @FXML
    public Button backButton;
    @FXML
    public ScrollPane scroll;
    @FXML
    public Hyperlink link;
    public Job selectedOffer;
    static final int V1DIMENSION = 810;
    static final int V2DIMENSION = 680;

    /**
     * this method is used to show the detailed job offer of the selected job
     *
     * @param offer the offer that was selected by the user (by clicking on it)
     */
    public void showOffer(Job offer) {

        this.selectedOffer = offer;
        this.jobTitle.setText(selectedOffer.getTitle());
        this.dateLabel.setText(selectedOffer.getDate());
        Text jobOffer = new Text(selectedOffer.getDescription());
        this.description.getChildren().add(jobOffer);
        this.link.setText(offer.getUrl());
        this.scroll.setContent(description);
        this.scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    /**
     * this the handler for the back button.
     * once the user clicks on this button he is redirected to the main view
     *
     * @param event click on back button
     * @throws IOException
     */
    @FXML
    public void handleBackAction(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(
                getClass()
                        .getResource("/view/MainView.fxml"));
        Scene scene = new Scene(root, V1DIMENSION, V2DIMENSION);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * method that opens the link of the job offer in the browser.
     * once the user clicks on the link his browser gets opened and he is redirected to the
     * job offer website
     *
     * @param event click on link
     */
    @FXML
    public void handleLink(ActionEvent event) {
        getHostServices().showDocument(link.getText());
    }


    @Override
    public void start(Stage primaryStage) {
    }


}
