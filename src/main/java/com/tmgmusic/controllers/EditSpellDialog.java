package com.tmgmusic.controllers;

import com.tmgmusic.App;
import com.tmgmusic.data.Character;
import com.tmgmusic.data.Spell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class EditSpellDialog
{
   private Character character;
   private Spell toEdit;

   @FXML private TextField spellNameField;
   @FXML private TextField filenameField;

   public void setCharacter(Character character)
   {
      this.character = character;
   }

   public void setSpell(Spell spell)
   {
      this.toEdit = spell;
      spellNameField.setText(toEdit.getName());
      filenameField.setText(toEdit.getAudio());
   }

   public void initialize()
   {

   }

   @FXML
   private void browse()
   {
      FileChooser chooser = new FileChooser();
      chooser.setInitialDirectory(new File(App.ROOT_DIR + File.separator + App.AUDIO_DIR));
      chooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("MP3", "*.mp3")
      );

      var newFile = chooser.showOpenDialog(null);
      if(newFile != null && newFile.exists())
      {
         filenameField.setText(newFile.getAbsolutePath());
      }

   }

   @FXML
   private void okClicked(ActionEvent evt)
   {
      toEdit.setName(spellNameField.getText());
      toEdit.setAudio(filenameField.getText());
      closeWindow(evt);
   }

   @FXML
   private void closeWindow(ActionEvent evt)
   {
      Node src = (Node)evt.getSource();
      Stage stage = (Stage)src.getScene().getWindow();
      stage.close();
   }
}
