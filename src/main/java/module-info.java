module com.tmgmusic {
    requires java.desktop;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires json.simple;
    requires io.github.classgraph;


    opens com.tmgmusic.controllers to javafx.fxml;
    exports com.tmgmusic;
}