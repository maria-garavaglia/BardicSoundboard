package com.tmgmusic.controllers;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.tmgmusic.App;
import com.tmgmusic.data.Character;
import com.tmgmusic.data.Spell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;

public class MainMenu extends MenuBar
{
   private Character loadedCharacter = null;
   private FileChooser fileChooser;
   private PropertyChangeSupport support;

   public MainMenu()
   {
      support = new PropertyChangeSupport(this);
      var loader = new FXMLLoader();
      loader.setRoot(this);
      loader.setControllerFactory(theClass -> this);

      try
      {
         loader.load(this.getClass().getResourceAsStream("/controllers/MainMenu.fxml"));
      }
      catch(IOException e)
      {
         throw new RuntimeException(e);
      }
   }

   public void addChangeListener(PropertyChangeListener pcl)
   {
      support.addPropertyChangeListener(pcl);
   }

   public void removeChangeListener(PropertyChangeListener pcl)
   {
      support.removePropertyChangeListener(pcl);
   }

   public void initialize()
   {
      // Set the FileChooser configuration
      fileChooser = new FileChooser();
      fileChooser.setTitle("Load Character");
      fileChooser.setInitialDirectory(new File(App.ROOT_DIR + File.separator + App.CHARACTERS_DIR));
      fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("JSON", "*.json")
      );
   }

   @FXML
   private void openFile()
   {
      var file = fileChooser.showOpenDialog(this.getScene().getWindow());
      if(file != null)
      {
         loadJson(file);
      }
   }

   private void loadJson(File jsonFile)
   {

      try(var reader = new FileReader(jsonFile))
      {
         JsonObject json = (JsonObject)Jsoner.deserialize(reader);
         var newCharacter = new Character(json);
         support.firePropertyChange("charLoaded", this.loadedCharacter, newCharacter);
         loadedCharacter = newCharacter;
      }
      catch(IOException | JsonException e)
      {
         e.printStackTrace();
      }
   }
}
