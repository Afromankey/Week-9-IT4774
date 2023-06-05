package com.example.testthermofx;

/**
 * JavaFX App
 */
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TestThermoApp extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        new FXMLLoader(TestThermoApp.class.getResource("Thermostat.fxml"));
        // Use class-level Scene object
        scene = new Scene(loadFXML("Thermostat"),820, 450);
        stage.setTitle("Program Settings");
        stage.setScene(scene);
        stage.show();
    }
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TestThermoApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}