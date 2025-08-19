package com.dearmariarenie.BardicSoundboard.controllers;

import com.dearmariarenie.BardicSoundboard.data.Character;
import com.dearmariarenie.BardicSoundboard.data.Spell;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainWindow
{
    private Character loadedCharacter = new Character();

    private FileChooser fileChooser;
    private ObservableList<Spell> songs;
    private MediaPlayer player;

    @FXML private VBox mainVBox;
    @FXML private ListView<Spell> songListView;
    @FXML private Label currentSong;
    @FXML private Slider volumeSlider;

    /****************************************************************************
     * Init
     */

    public void initialize()
    {
        // Add listener for volume slider (not sure how to add it in fxml)
        volumeSlider.valueProperty().addListener(this::setVolume);

        // Set the FileChooser configuration
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
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

    /****************************************************************************
     * Spell List Operations
     */

    @FXML
    private void addSpell() throws IOException
    {
        var fxmlLoader = new FXMLLoader(getClass().getResource("AddSpellDialog.fxml"));
        Parent parent = fxmlLoader.load();
        var controller = fxmlLoader.<AddSpellDialog>getController();

        var scene = new Scene(parent);
        var stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

        var newSpell = controller.getNewSpell();
        if(newSpell != null)
        {
            loadedCharacter.addSpell(newSpell);
            songs.add(newSpell);
        }

    }

    @FXML
    private void editSpell() throws IOException
    {
        Spell spell = songListView.getSelectionModel().getSelectedItem();
        if(spell != null)
        {
            var fxmlLoader = new FXMLLoader(getClass().getResource("EditSpellDialog.fxml"));
            Parent parent = fxmlLoader.load();
            var controller = fxmlLoader.<EditSpellDialog>getController();

            // If spell is changed, loadedCharacter should be updated automatically.
            // Thanks, Java pointer-not-pointers!
            controller.setSpell(spell);

            var scene = new Scene(parent);
            var stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }

    }

    @FXML
    private void removeSpell()
    {
        if(songListView.getSelectionModel().getSelectedItem() == null)
        {
            return;
        }

        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setHeaderText(null);
        confirmDialog.setContentText("Are you sure you want to delete this spell?");
        confirmDialog.showAndWait();

        if(confirmDialog.getResult() == ButtonType.OK)
        {
            Spell toDelete = songListView.getSelectionModel().getSelectedItem();
            loadedCharacter.removeSpell(toDelete);
            songs.remove(toDelete);
        }
    }

    /****************************************************************************
     * Audio playback
     */

    @FXML
    private void play()
    {
        Spell song = songListView.getSelectionModel().getSelectedItem();
        var file = new File(song.getAudio());
        if(!file.exists())
        {
            System.err.println("ERROR Audio file \"" + file.toURI().toString() + "\" does not exist.");
            return;
        }

        System.out.println("Playing audio: " + file.toURI().toString());
        Media media = new Media(file.toURI().toString());

        if(player != null && player.getStatus() == MediaPlayer.Status.PLAYING)
        {
            player.stop();
        }

        player = new MediaPlayer(media);
        player.setOnError(() ->
        {
            throw player.getError();
        });

        player.setVolume(volumeSlider.getValue());
        player.play();
        currentSong.setText(song.getName());
    }

    @FXML
    private void stop()
    {
        if(player != null && player.getStatus() == MediaPlayer.Status.PLAYING)
        {
            player.stop();
        }
    }

    private void setVolume(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
    {
        if(player != null)
        {
            player.setVolume(newValue.doubleValue());
        }
    }

    /****************************************************************************
     * File menu
     */

    @FXML
    private void open()
    {
        fileChooser.setTitle("Load Character");
        var file = fileChooser.showOpenDialog(mainVBox.getScene().getWindow());
        if(file != null)
        {
            loadedCharacter = new Character(file);

            songs.clear();
            songs.addAll(loadedCharacter.getSpells());
        }
    }

    @FXML
    private void save()
    {
        var file = new File(loadedCharacter.getSaveFile());
        if(!file.exists())
        {
            saveAs();
        }

        saveFile(file);

        System.out.println("Character saved to '" + file.getAbsolutePath() + "'");
    }

    @FXML
    private void saveAs()
    {
        fileChooser.setTitle("Save Character");
        var file = fileChooser.showSaveDialog(mainVBox.getScene().getWindow());
        saveFile(file);
    }

    private void saveFile(File file)
    {
        String newJson = loadedCharacter.toJson();
        try
        {
            Files.writeString(file.toPath(), newJson);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

}
