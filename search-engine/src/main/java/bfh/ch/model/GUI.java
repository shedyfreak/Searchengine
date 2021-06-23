package bfh.ch.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class GUI extends Application {
    static final int V1DIMENSION = 790;
    static final int V2DIMENSION = 680;

    /**
     * this method starts the javafx application.
     * it loads the views into the scene and sets the icon and logo
     * of the application
     *
     * @param stage main element of our javafx view
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(
                getClass()
                        .getResource("/view/MainView.fxml"));
        Scene scene = new Scene(root, V1DIMENSION, V2DIMENSION);
        stage.setTitle("exploring search project");
        stage.setScene(scene);
        stage.getIcons().add(new Image("icon.png"));
        stage.show();
    }

    /**
     * main method to launch the application
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);

    }

}
