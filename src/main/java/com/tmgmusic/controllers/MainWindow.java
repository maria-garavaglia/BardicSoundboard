package com.tmgmusic.controllers;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;

public class MainWindow
{

   private ObservableList<Spell> songs;
   private FileChooser fileChooser;
   private MediaPlayer player;

   @FXML
   private VBox mainVBox;

   @FXML
   private ListView<Spell> songListView;

   @FXML
   private Label currentSong;

   public void initialize() throws MalformedURLException
   {
      // Set the FileChooser configuration
      fileChooser = new FileChooser();
      fileChooser.setTitle("Choose a music");
      fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
      fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("MP3", "*.mp3")
      );

      // Initialize the list
      songs = FXCollections.observableArrayList();

      load("cfg/Hrothgar.json");

      songListView.setItems(songs);
      songListView.getSelectionModel().selectFirst();
   }

   @FXML
   public void play()
   {
      Spell song = songListView.getSelectionModel().getSelectedItem();
      System.out.println("Playing audio: " + song.getAudio().toURI().toString());
      Media media = new Media(song.getAudio().toURI().toString());

      if(player != null && player.getStatus() == MediaPlayer.Status.PLAYING)
      {
         player.stop();
      }

      player = new MediaPlayer(media);
      player.setOnError(new Runnable()
      {
         @Override
         public void run()
         {
            player.getError().printStackTrace();
         }
      });

      player.setVolume(0.2);
      player.play();
      currentSong.setText(song.getName());
   }

   @FXML
   public void stop()
   {
      if(player == null)
      {
         return;
      }
      else if(player.getStatus() == MediaPlayer.Status.PLAYING)
      {
         player.stop();
      }
   }

   private void load(String jsonFile)
   {

      try
      {
         var reader = new FileReader(jsonFile);
         JsonObject json = (JsonObject)Jsoner.deserialize(reader);
         Character newChar = new Character(json);
         var spellList = newChar.getSpells();

         for(var spellName : spellList.keySet())
         {
            Spell newSpell = spellList.get(spellName);
            songs.add(newSpell);
         }


      }
      catch(JsonException | FileNotFoundException e)
      {
         e.printStackTrace();
      }
   }

}
