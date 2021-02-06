module BardicSoundboard {
   requires javafx.controls;
   requires javafx.fxml;
   requires javafx.media;

   exports sample to javafx.graphics, javafx.fxml;

   opens sample to javafx.fxml;
}