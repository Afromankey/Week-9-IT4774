package com.example.testthermofx;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondScreenController {
    // Formatter to display 24-hour time (hours & minutes)
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    // List of days of the week for day spinner
    private final List<String> weekDays = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday");
    private LocalTime initialTime = LocalTime.now();
    @FXML
    private Label dowLabel;
    @FXML
    private Spinner<String> daySpinner;
    @FXML
    private Label todLabel;
    @FXML
    private Spinner<LocalTime> timeSpinner;
    @FXML
    private Slider levelSlider;
    @FXML
    private Label levelDisplay;
    @FXML
    private Button saveButton;
    @FXML
    private Button returnButton;
    @FXML
    private TextArea settingsListing;

    public void initialize() {
        String borderStyle = "-fx-border-color: black; -fx-font-size: 21";
        String labelStyle = "-fx-font-size: 21";
        daySpinner.setStyle(borderStyle);
        timeSpinner.setStyle(borderStyle);
        // Set up the day spinner with value factory for days of week
        daySpinner.setValueFactory(new SpinnerValueFactory
                .ListSpinnerValueFactory<>(FXCollections
                .observableList(weekDays)));
        // Set day of week to today
        daySpinner.getValueFactory().setValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE")));

        // Set up the time spinner with value factory for time of day
        SpinnerValueFactory<LocalTime> timeValueFactory = new SpinnerValueFactory<>() {
            @Override
            // Method called when spinner's down arrow is clicked
            public void decrement(int steps) {
                LocalTime time = getValue();
                setValue(time.minusMinutes(steps));
            }

            @Override
            // Method called when spinner's up arrow is clicked
            public void increment(int steps) {
                LocalTime time = getValue();
                setValue(time.plusMinutes(steps));
            }
        };
        // Need custom converters to convert between LocalTime and String
        timeValueFactory.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalTime object) {
                if (object == null) {
                    return "";
                }
                return dateTimeFormatter.format(object);
            }

            @Override
            public LocalTime fromString(String string) {
                if (string == null || string.isEmpty()) {
                    return null;
                } else {
                    return LocalTime.parse(string, dateTimeFormatter);
                }
            }
        });

        // Set time in time spinner's value factory to current time
        timeValueFactory.setValue(initialTime);
        timeSpinner.setValueFactory(timeValueFactory);
        // Set up level slider
        configureSlider();
        // Set style for day of week and time of day labels
        dowLabel.setStyle(labelStyle);
        todLabel.setStyle(labelStyle);
        // Set style for level display label
        levelDisplay.setStyle(labelStyle);
        displaySettings();

        saveButton.setOnAction(event -> {
            // Convert day of week to integer for storage
            // Note use of switch expression rather than switch statement
            int day = switch(daySpinner.getValue()) {
                case "Sunday" -> 0;
                case "Monday" -> 1;
                case "Tuesday" -> 2;
                case "Wednesday" -> 3;
                case "Thursday" -> 4;
                case "Friday" -> 5;
                case "Saturday" -> 6;
                // Should never happen
                default -> throw new IllegalStateException("Unexpected value: " + daySpinner.getValue());
            };
            // Create a new Setting object and write it to file
            Setting setting = new Setting(day, timeSpinner.getValue(), (int) levelSlider.getValue());
            DataTools.writeFileData(setting);
            settingsListing.clear();
            displaySettings();
        });

        // Return to StartScreen
        returnButton.setOnAction(actionEvent -> {
            try {
                TestThermoApp.setRoot("Thermostat");
            } catch (IOException ex) {
                System.out.println("Unable to return to StartScreen: " + ex.getMessage());
            }
        });
    }

    // Configure the level slider's appearance and bind the level display label
    // to the slider's value
    private void configureSlider() {
        levelSlider.setMin(0);
        levelSlider.setMax(100);
        levelSlider.setShowTickLabels(true);
        levelSlider.setBlockIncrement(10);
        StringConverter<Number> converter = new NumberStringConverter();
        levelDisplay.textProperty().bind(levelSlider.valueProperty().asString("Level: %.0f%%"));
    }

    // Read saved settings from file and display them in the text area
    private void displaySettings() {
        ArrayList<Setting> settings = DataTools.readFileData();
        String settingsString = "";
        for (Setting s : settings) {
            settingsString += s.toString() + "\n";
        }
        settingsListing.setText(settingsString);
    }
}