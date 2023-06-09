module edu.capella.bsit.dimmerswitch {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;


    opens edu.capella.bsit.dimmerswitch to javafx.fxml;
    exports edu.capella.bsit.dimmerswitch;
}