module BardicSoundboard {
   requires javafx.controls;
   requires javafx.fxml;
   requires javafx.media;
   requires json.simple;

   exports com.tmgmusic.bard.controllers to javafx.graphics, javafx.fxml;

   opens com.tmgmusic.bard.controllers to javafx.fxml;
}