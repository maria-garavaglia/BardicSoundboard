package com.dearmariarenie.BardicSoundboard.controllers;

import com.dearmariarenie.BardicSoundboard.data.Config;
import com.dearmariarenie.BardicSoundboard.data.Spell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AddSpellDialog
{
    @FXML private TextField spellNameField;
    @FXML private TextField filenameField;

    private Spell newSpell = null;

    public void initialize()
    {

    }

    public Spell getNewSpell()
    {
        return newSpell;
    }

    @FXML
    private void browse()
    {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(Config.audioDir));
        chooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("MP3", "*.mp3")
        );

        var newFile = chooser.showOpenDialog(null);
        if(newFile != null && newFile.exists())
        {
            filenameField.setText(newFile.getName());
        }
    }

    @FXML
    private void okClicked(ActionEvent evt)
    {
        newSpell = new Spell(
            spellNameField.getText(),
            filenameField.getText()
        );

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
