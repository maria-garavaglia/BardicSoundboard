module com.tmgmusic {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires json.simple;

    opens com.tmgmusic.controllers to javafx.fxml;
    exports com.tmgmusic;
}