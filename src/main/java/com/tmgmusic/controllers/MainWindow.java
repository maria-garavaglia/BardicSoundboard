package com.tmgmusic.controllers;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.tmgmusic.App;
import com.tmgmusic.data.Character;
import com.tmgmusic.data.Spell;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MainWindow
{
   private Character loadedCharacter;

   private FileChooser fileChooser;
   private ObservableList<Spell> songs;
   private MediaPlayer player;

   @FXML
   private VBox mainVBox;
   @FXML
   private ListView<Spell> songListView;
   @FXML
   private Label currentSong;
   @FXML
   private Slider volumeSlider;

   /****************************************************************************
    * Init
    */

   public void initialize()
   {
      // Add listener for volume slider (not sure how to add it in fxml)
      volumeSlider.valueProperty().addListener(this::setVolume);

      // Set the FileChooser configuration
      fileChooser = new FileChooser();
      fileChooser.setInitialDirectory(new File(App.ROOT_DIR + File.separator + App.CHARACTERS_DIR));
      fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("JSON", "*.json")
      );

      // Initialize the list
      songs = FXCollections.observableArrayList();

      songListView.setItems(songs);
      songListView.getSelectionModel().selectFirst();
      songListView.setOnMouseClicked(click ->
      {
         if(click.getClickCount() == 2)
         {
            play();
         }
      });
   }

   /****************************************************************************
    * Spell List Operations
    */

   @FXML
   private void addSpell()
   {

   }

   @FXML
   private void editSpell()
   {

   }

   @FXML
   private void removeSpell()
   {

   }

   /****************************************************************************
    * Audio playback
    */

   @FXML
   private void play()
   {
      Spell song = songListView.getSelectionModel().getSelectedItem();
      String filePath = new File(App.ROOT_DIR + File.separator + song.getAudio()).toURI().toString();
      System.out.println("Playing audio: " + filePath);
      Media media = new Media(filePath);

      if(player != null && player.getStatus() == MediaPlayer.Status.PLAYING)
      {
         player.stop();
      }

      player = new MediaPlayer(media);
      player.setOnError(() ->
      {
         throw player.getError();
      });

      player.setVolume(volumeSlider.getValue());
      player.play();
      currentSong.setText(song.getName());
   }

   @FXML
   private void stop()
   {
      if(player != null && player.getStatus() == MediaPlayer.Status.PLAYING)
      {
         player.stop();
      }
   }

   private void setVolume(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
   {
      if(player != null)
      {
         player.setVolume(newValue.doubleValue());
      }
   }

   /****************************************************************************
    * File menu
    */

   @FXML
   private void open()
   {
      fileChooser.setTitle("Load Character");
      var file = fileChooser.showOpenDialog(mainVBox.getScene().getWindow());
      if(file != null)
      {
         loadedCharacter = new Character(file);

         songs.clear();
         songs.addAll(loadedCharacter.getSpells());
      }
   }

   @FXML
   private void save()
   {
      var file = new File(loadedCharacter.getSaveFile());
      if(!file.exists())
      {
         saveAs();
      }

      saveFile(file);
   }

   @FXML
   private void saveAs()
   {
      fileChooser.setTitle("Save Character");
      var file = fileChooser.showSaveDialog(mainVBox.getScene().getWindow());
      saveFile(file);
   }

   private void saveFile(File file)
   {
      String newJson = loadedCharacter.toJson();
      try
      {
         Files.writeString(file.toPath(), newJson);
      }
      catch(IOException e)
      {
         e.printStackTrace();
      }
   }

}
