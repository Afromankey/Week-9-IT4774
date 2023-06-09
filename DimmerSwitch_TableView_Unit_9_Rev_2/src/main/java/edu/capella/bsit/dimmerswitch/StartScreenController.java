package edu.capella.bsit.dimmerswitch;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class StartScreenController {
    @FXML
    private Button clickHereButton;

    public void initialize() {
        clickHereButton.setOnAction(actionEvent -> {
            try {
                DimmerApplication.setRoot("ProgramSettings");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
