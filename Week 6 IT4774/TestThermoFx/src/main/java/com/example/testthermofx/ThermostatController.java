package com.example.testthermofx;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ThermostatController {
    // Controls that need to be accessed from code annotated with @FXML
    @FXML
    private Label currentTemperature;
    @FXML
    private Label currentHumidity;
    @FXML
    Spinner<Integer> currentSetting;
    @FXML
    private Label currentTime;
    @FXML
    private Button clickHereButton;

    // Method to handle initialization of controls
    public void initialize() {
        String borderStyle = "-fx-border-color: black; -fx-font-size: 33";
        currentTemperature.setStyle(borderStyle);
        currentHumidity.setStyle(borderStyle);
        currentSetting.setStyle(borderStyle);
        initSpinner();
        initTime();

        // Set the button action
        clickHereButton.setOnAction(actionEvent -> {
            try {
                TestThermoApp.setRoot("ProgramSettings");
            } catch (IOException ex) {
                System.out.println("Error loading ProgramSettings.fxml: " + ex.getMessage());
            }
        });
    }

    // Method to set temperature spinner, range 50 - 90
    private void initSpinner() {
        currentSetting.setValueFactory(
                new SpinnerValueFactory
                        .IntegerSpinnerValueFactory(50, 90)
        );
    }

    // Method to check time and update time displayed
    private void initTime() {
        final DateTimeFormatter format = DateTimeFormatter.ofPattern("hh:mm a");
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                event -> {
                    currentTime.setText(format.format(LocalDateTime.now()));
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
