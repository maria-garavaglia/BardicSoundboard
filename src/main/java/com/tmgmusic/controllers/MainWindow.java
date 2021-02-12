package com.tmgmusic.controllers;

import com.tmgmusic.data.Character;
import com.tmgmusic.data.Spell;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MainWindow implements PropertyChangeListener
{

   private ObservableList<Spell> songs;
   private MediaPlayer player;

   @FXML
   private MainMenu menu;

   @FXML
   private ListView<Spell> songListView;

   @FXML
   private Label currentSong;

   public void initialize()
   {
      // Initialize the list
      songs = FXCollections.observableArrayList();

      songListView.setItems(songs);
      songListView.getSelectionModel().selectFirst();
      songListView.setOnMouseClicked(new EventHandler<MouseEvent>()
      {
         @Override
         public void handle(MouseEvent click)
         {
            if(click.getClickCount() == 2)
            {
               play();
            }
         }
      });

      menu.addChangeListener(this);
   }

   @FXML
   private void play()
   {
      Spell song = songListView.getSelectionModel().getSelectedItem();
      System.out.println("Playing audio: " + song.getAudio().toURI().toString());
      Media media = new Media(song.getAudio().toURI().toString());

      if(player != null && player.getStatus() == MediaPlayer.Status.PLAYING)
      {
         player.stop();
      }

      player = new MediaPlayer(media);
      player.setOnError(() -> player.getError().printStackTrace());

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

   @Override
   public void propertyChange(PropertyChangeEvent evt)
   {
      this.loadCharacter((Character)evt.getNewValue());
   }

   private void loadCharacter(Character newCharacter)
   {
      songs.removeAll();
      songs.addAll(newCharacter.getSpells().values());
   }
}
