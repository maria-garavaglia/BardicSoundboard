package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.MalformedURLException;

public class Controller
{
   public Label helloWorld;

   private GridPane gridPane;

   private FileChooser fileChooser;
   private MediaPlayer player;

   public void initialize()
   {
      fileChooser = new FileChooser();
      fileChooser.setTitle("Choose music");
      fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
      fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("MP3", "*.mp3")
      );
   }

   @FXML
   private void sayHelloWorld(ActionEvent actionEvent)
   {
      File file = fileChooser.showOpenDialog(gridPane);
      Media sound;
      try
      {
         sound = new Media(file.toURI().toURL().toExternalForm());
      }
      catch(MalformedURLException e)
      {
         e.printStackTrace();
      }

      if(player == null)
      {
         player = new MediaPlayer(sound);
         System.out.println("Playing");
         player.play();
         return;
      }

      if(player.getStatus() == MediaPlayer.Status.PLAYING)
      {
         player.stop();
      }
      player = new MediaPlayer(sound);

      System.out.println("Playing");
      player.play();
   }
}
