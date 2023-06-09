package edu.capella.bsit.dimmerswitch;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class DimmerProgramController {
    // Formatter to display 24-hour time (hours & minutes)
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    // List of days of the week for day spinner
    private final List<String> weekDays = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday", "Weekend", "Holiday"); // Added Weekend and Holiday
    private LocalTime initialTime = LocalTime.now();
    @FXML
    private Label dowLabel;
    @FXML
    private Spinner<String> daySpinner;
    @FXML
    private Label todLabel; // Added
    @FXML
    private Spinner<LocalTime> timeSpinner;
    @FXML
    private DatePicker holidayDatePicker; // Added
    @FXML
    private Label holidayCalendarLabel; // Added
    @FXML
    private Slider levelSlider;
    @FXML
    private Label levelDisplay;
    @FXML
    private Button saveButton;
    @FXML
    private Button returnButton;
    @FXML
    // Table view to display settings. Type parameter is the type of the
    // object that is source for data.
    private TableView<Setting> settingsListing;
    // Table includes the following 3 columns
    // 1st type parameter is the tpe of the object that is source for data.
    // 2nd type parameter is the type of the data contained within the column.
    @FXML
    private TableColumn<Setting, String> dayColumn;
    @FXML
    private TableColumn<Setting, LocalTime> timeColumn;
    @FXML
    private TableColumn<Setting, Double> levelColumn;
    @FXML
    private Button deleteButton;

    public void initialize() {
        String borderStyle = "-fx-border-color: black; -fx-font-size: 21";
        String labelStyle = "-fx-font-size: 21";
        daySpinner.setStyle(borderStyle);
        timeSpinner.setStyle(borderStyle);
        holidayDatePicker.setStyle(borderStyle); // Added
        // Hide the holiday date picker
        holidayDatePicker.setVisible(false);   // Added
        holidayCalendarLabel.setVisible(false);   // Added
        holidayCalendarLabel.setStyle(labelStyle);  // Added

        // Shift the holiday date picker to the left
        holidayDatePicker.setTranslateX(-200);  // Added
        holidayCalendarLabel.setTranslateX(-200); // Added

        // Configure the day spinner's value factory
        daySpinner.setValueFactory(new SpinnerValueFactory
                .ListSpinnerValueFactory<>(FXCollections
                .observableArrayList(weekDays)));
        daySpinner.getValueFactory().setValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEEE")));

        daySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Weekend")) {
                timeSpinner.setVisible(true);
                timeSpinner.setOpacity(0.5);
                timeSpinner.setDisable(true);
                todLabel.setVisible(true);
                todLabel.setOpacity(0.5);
                holidayDatePicker.setVisible(false);
                holidayCalendarLabel.setVisible(false);
            } else if (newValue.equals("Holiday")) {
                timeSpinner.setVisible(false);
                todLabel.setVisible(false);
                holidayDatePicker.setVisible(true);
                holidayCalendarLabel.setVisible(true);
            } else {
                timeSpinner.setVisible(true);
                timeSpinner.setOpacity(1.0);
                timeSpinner.setDisable(false);
                todLabel.setVisible(true);
                todLabel.setOpacity(1.0);
                holidayDatePicker.setVisible(false);
                holidayCalendarLabel.setVisible(false);
            }
        });

        // Set up the time spinner with value factory for time of day
        SpinnerValueFactory<LocalTime> timeValueFactory = new SpinnerValueFactory<>() {
            // Method called by JavaFX runtime when down arrow is clicked
            @Override
            public void decrement(int steps) {
                if (getValue() == null) {
                    setValue(initialTime);
                } else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.minusMinutes(steps));
                }
            }

            // Method called by JavaFX runtime when up arrow is clicked
            @Override
            public void increment(int steps) {
                if (this.getValue() == null) {
                    setValue(initialTime);
                } else {
                    LocalTime time = (LocalTime) getValue();
                    setValue(time.plusMinutes(steps));
                }
            }
        };

        // Convert time to string and vice versa
        timeValueFactory.setConverter(new StringConverter<>() {
            @Override
            public String toString(LocalTime localTime) {
                if(localTime == null) {
                    return "";
                }
                return dateTimeFormatter.format(localTime);
            }

            @Override
            public LocalTime fromString(String string) {
                if(string == null || string.isEmpty()) {
                    return null;
                }
                else {
                    return LocalTime.parse(string, dateTimeFormatter);
                }
            }
        });

        timeValueFactory.setValue(initialTime);
        timeSpinner.setValueFactory(timeValueFactory);
        timeSpinner.setEditable(true);
        configureSlider();

        dowLabel.setStyle(labelStyle);
        todLabel.setStyle(labelStyle);
        levelDisplay.setStyle(labelStyle);
        displaySettings(Path.of("dimmer.json"));

        saveButton.setOnAction(event -> {
            // Convert day of week name to number (zero-based)
            int day = switch(daySpinner.getValue()) {
                case "Sunday" -> 0;
                case "Monday" -> 1;
                case "Tuesday" -> 2;
                case "Wednesday" -> 3;
                case "Thursday" -> 4;
                case "Friday" -> 5;
                case "Saturday" -> 6;
                case "Weekend" -> 7; // Added for unit 9 example
                case "Holiday" -> 8; // Added for unit 9 example
                default -> throw new IllegalArgumentException("Unexpected value: " + daySpinner.getValue());
            };

            // Added logic to handle weekend and holiday along with day of week
            Setting setting = null;
            // Specific day of week
            if(day < 7) {
                setting = new Setting(day, timeSpinner.getValue(), (int) levelSlider.getValue());
            }
            // Weekend
            else if(day == 7) { // Added for unit 9 example
                setting = new Setting(day, LocalTime.of(0, 0), (int) levelSlider.getValue());
            }
            // Holiday
            else if(day == 8) { // Added for unit 9 example
                setting = new Setting(day, LocalTime.of(0, 0), (int) levelSlider.getValue());
                setting.setHoliday(holidayDatePicker.getValue()
                    .format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
            }

            // Write data to JSON file
            DataTools.writeFileData(Path.of("dimmer.json"), setting);
            // Read back & display settings in TableView
            displaySettings(Path.of("dimmer.json"));
        });

        returnButton.setOnAction(actionEvent -> {
            try {
                // Remember this is just for the example. Your program
                // should show the start screen when the user clicks
                // the return button.
                DimmerApplication.setRoot("StartScreen");
            }
            catch(IOException ex) {
                System.out.println("Unable to return to start screen: " + ex.getMessage());
            }
        });

        // Value factory for the day column in TableView
        dayColumn.setCellValueFactory(cell -> {
            // Convert day of week number to 3-letter abbreviation
            String dayName = switch (cell.getValue().getDayOfWeek()) {
                case 0 -> "Sun";
                case 1 -> "Mon";
                case 2 -> "Tue";
                case 3 -> "Wed";
                case 4 -> "Thu";
                case 5 -> "Fri";
                case 6 -> "Sat";
                case 7 -> "W/E"; // Added to deal with weekend for unit 9 example
                // Added to deal with holiday for unit 9 example
                case 8 -> "Hol " + cell.getValue().getHoliday();
                default -> "";
            };
            return new ReadOnlyObjectWrapper<>(dayName);
        });

        // Value factory for the time column in TableView - value used directly
        timeColumn.setCellValueFactory(cell -> {
            // Round time to nearest minute
            LocalTime roundedTime = cell.getValue().getTime().truncatedTo(ChronoUnit.MINUTES);
            // Return object that the table column can display
            return new ReadOnlyObjectWrapper<>(roundedTime);
        });
        // Value factory for the level column in TableView - value used directly
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));

        // Remove selected setting from the table view and the file
        deleteButton.setOnAction(actionEvent -> {
            Setting setting = settingsListing.getSelectionModel().getSelectedItem();
            settingsListing.getItems().remove(setting);
            List<Setting> settings = settingsListing.getItems();
            DataTools.updateFile(Path.of("dimmer.json"), settings);
        });
    }

    // Configure the level slider's appearance and bind the level display label
    // to the slider's value
    private void configureSlider() {
        levelSlider.setMin(0);
        levelSlider.setMax(100);
        levelSlider.setShowTickLabels(true);
        levelSlider.setBlockIncrement(5); // How much slider moves when clicked
        levelSlider.setMajorTickUnit(20);   // Set tick marks
        StringConverter<Number> converter = new NumberStringConverter();
        levelDisplay.textProperty().bind(levelSlider.valueProperty().asString("Level: %.0f%%"));
    }

    // Read saved settings from file and display them in the table view
    private void displaySettings(Path path) {
        ArrayList<Setting> settings = DataTools.readFileData(path);
        // Sort settings by day of week and time of day
        settings.sort(Comparator.comparing((Setting setting) -> setting.getDayOfWeek())
                .thenComparing(setting -> setting.getTime()));
        // Create ObservableList & display it in the table view
        ObservableList<Setting> observableSettings = FXCollections.observableList(settings);
        settingsListing.setItems(observableSettings);
    }
}