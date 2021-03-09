package com.tmgmusic.controllers;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.tmgmusic.App;
import com.tmgmusic.data.Character;
import com.tmgmusic.data.Spell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

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

   public void initialize()
   {
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

      player.setVolume(0.2);
      player.play();
      currentSong.setText(song.getName());
   }

   @FXML
   public void stop()
   {
      if(player != null && player.getStatus() == MediaPlayer.Status.PLAYING)
      {
         player.stop();
      }
   }

   @FXML
   private void openFile()
   {
      fileChooser.setTitle("Load Character");
      var file = fileChooser.showOpenDialog(mainVBox.getScene().getWindow());
      if(file != null)
      {
         try(var reader = new FileReader(file))
         {
            JsonObject json = (JsonObject)Jsoner.deserialize(reader);
            loadedCharacter = new Character(json);

         }
         catch(IOException | JsonException e)
         {
            e.printStackTrace();
         }

         songs.clear();
         songs.addAll(loadedCharacter.getSpells());
      }
   }

   @FXML
   private void saveFile() throws IOException
   {
      var writer = new StringWriter();
      String jsonString = loadedCharacter.toJson();
      System.out.println(jsonString);
   }

   @FXML
   private void saveFileAs()
   {
      fileChooser.setTitle("Save Character");
      var file = fileChooser.showSaveDialog(mainVBox.getScene().getWindow());
      //String json = loadedCharacter.toJson();

   }
}
