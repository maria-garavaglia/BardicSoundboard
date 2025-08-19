package com.dearmariarenie.BardicSoundboard.controllers;

import com.dearmariarenie.BardicSoundboard.data.Spell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class SpellView
{
    private Spell toEdit = new Spell("", "");

    @FXML private TextField spellNameField;
    @FXML private TextField filenameField;

    private final FileChooser fileChooser = new FileChooser();

    public void setSpell(Spell spell)
    {
        this.toEdit = spell;
        spellNameField.setText(toEdit.getName());
        filenameField.setText(toEdit.getAudio());
    }

    public Spell getSpell()
    {
        return toEdit;
    }

    public void initialize()
    {
        fileChooser.setInitialDirectory(new File("Audio"));
    }

    @FXML
    private void browse()
    {
        var newFile = fileChooser.showOpenDialog(null);
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
