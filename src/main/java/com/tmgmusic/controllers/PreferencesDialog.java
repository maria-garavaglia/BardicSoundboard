package com.tmgmusic.controllers;

import com.tmgmusic.App;
import com.tmgmusic.data.Config;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class PreferencesDialog
{
    @FXML TextField charactersDirField;
    @FXML TextField audioDirField;

    public void initialize()
    {
        charactersDirField.setText(Config.charactersDir);
        audioDirField.setText(Config.audioDir);
    }

    @FXML
    private void browseCharacters()
    {
        var dirChooser = new DirectoryChooser();
        dirChooser.setInitialDirectory(new File(Config.charactersDir).getParentFile());

        var newFile = dirChooser.showDialog(null);
        if(newFile != null && newFile.exists())
        {
            charactersDirField.setText(newFile.getAbsolutePath());
        }
    }

    @FXML
    private void browseAudio()
    {
        var dirChooser = new DirectoryChooser();
        dirChooser.setInitialDirectory(new File(Config.audioDir).getParentFile());

        var newFile = dirChooser.showDialog(null);
        if(newFile != null && newFile.exists())
        {
            audioDirField.setText(newFile.getAbsolutePath());
        }
    }

    @FXML
    private void okClicked(ActionEvent evt)
    {
        Config.charactersDir = charactersDirField.getText();
        Config.audioDir = audioDirField.getText();

        App.config.save();

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
