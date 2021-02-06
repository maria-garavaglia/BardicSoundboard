package com.tmgmusic.bard.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import com.tmgmusic.bard.data.Song;

import java.io.File;
import java.net.MalformedURLException;

public class Controller {

   private ObservableList<Song> songs;
   private FileChooser fileChooser;
   private MediaPlayer player;

   @FXML
   private VBox mainVBox;

   @FXML
   private ListView<Song> songListView;

   @FXML
   private Label currentSong;

   public void initialize() {
      // Set the FileChooser configuration
      fileChooser = new FileChooser();
      fileChooser.setTitle("Choose a music");
      fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
      fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("MP3", "*.mp3")
      );

      // Initialize the list
      songs = FXCollections.observableArrayList();
      songListView.setItems(songs);
      songListView.getSelectionModel().selectFirst();
   }

   @FXML
   public void addSong() throws MalformedURLException {
      File file = fileChooser.showOpenDialog(mainVBox.getScene().getWindow());
      if (file != null) {
         songs.add(new Song(file.getName(), file.toURI().toURL()));
         songListView.setItems(songs);
      } else {
         System.out.println("File not found");
      }
   }

   @FXML
   public void play() {
      Song song = songListView.getSelectionModel().getSelectedItem();
      Media media = new Media(song.getUrl().toExternalForm());
      if (player == null) {
         player = new MediaPlayer(media);
         player.play();
         currentSong.setText(song.getName());
         MediaException error = player.getError();
         if(error == null)
         {
            System.out.println("No errors");
         }
         else
         {
            System.err.println(error.getMessage());
         }
         return;
      }

      if (player.getStatus() == MediaPlayer.Status.PLAYING) {
         player.stop();
      }
      player = new MediaPlayer(media);
      player.play();
      currentSong.setText(song.getName());

      MediaException error = player.getError();
      if(error == null)
      {
         System.out.println("No errors");
      }
      else
      {
         System.err.println(error.getMessage());
      }
   }

   @FXML
   public void stop() {
      if (player == null) {
         return;
      } else if (player.getStatus() == MediaPlayer.Status.PLAYING) {
         player.stop();
      }
   }

}
