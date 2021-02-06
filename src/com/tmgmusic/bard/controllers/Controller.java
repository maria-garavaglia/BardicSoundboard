package com.tmgmusic.bard.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import com.tmgmusic.bard.data.Song;

import java.io.File;
import java.net.MalformedURLException;

public class Controller
{

   private ObservableList<Song> songs;
   private FileChooser fileChooser;
   private MediaPlayer player;

   @FXML
   private VBox mainVBox;

   @FXML
   private ListView<Song> songListView;

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

      var folder = new File("C:/Users/tgaravaglia/Music/Brutal_Legend/Solos/Eddie/");
      for(var song : folder.listFiles())
      {
         songs.add(new Song(song.getName(), song.toURI().toURL()));
      }
//      var working = new File("E:/Users/tyler.garavaglia/Music/MediaMonkey/Fats Waller/Unknown Album/01 Chant of the Groove.mp3");
      var working = new File("C:/Users/tgaravaglia/Downloads/test.mp3");
//      var working = new File("C:/Users/tgaravaglia/Downloads/yt1s.com - 1939 HITS ARCHIVE Undecided  Chick Webb Ella Fitzgerald vocal_test.mp3");
      songs.add(new Song(working.getName(), working.toURI().toURL()));
      songListView.setItems(songs);
      songListView.getSelectionModel().selectFirst();
   }

   @FXML
   public void addSong() throws MalformedURLException
   {
      File file = fileChooser.showOpenDialog(mainVBox.getScene().getWindow());
      if(file != null)
      {
         songs.add(new Song(file.getName(), file.toURI().toURL()));
         songListView.setItems(songs);
      }
      else
      {
         System.out.println("File not found");
      }
   }

   private int time = 0;

   @FXML
   public void play()
   {
      Song song = songListView.getSelectionModel().getSelectedItem();
      System.out.println("Playing audio: " + song.getUrl().toExternalForm());
      Media media = new Media(song.getUrl().toExternalForm());

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

}
