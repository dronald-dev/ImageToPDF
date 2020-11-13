package ua.dronald.imagetopdf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ImageToPDF extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ImageToPDF.fxml"));
        Scene scene = new Scene(root);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Image To PDF");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        ImageToPDF.launch(args);
    }

}
