module com.example.testthermofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires org.controlsfx.controls;

    opens com.example.testthermofx to javafx.fxml;
    exports com.example.testthermofx;
}