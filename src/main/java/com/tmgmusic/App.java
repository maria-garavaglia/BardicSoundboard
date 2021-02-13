package com.tmgmusic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final String ROOT_DIR = System.getProperty("user.home") + File.separator + "Bardic Soundboard";
    private static final String CHARACTERS_DIR = "/Characters";
    private static final String AUDIO_DIR = "/Audio";

    public static final String CHARACTERS_DIR_FULL = ROOT_DIR + CHARACTERS_DIR;
    public static final String AUDIO_DIR_FULL = ROOT_DIR + AUDIO_DIR;

    private static Scene scene;

    public static void main(String[] args) throws IOException
    {
        findOrCreateHomeDirs();

        //Launch FX
        launch();
    }

    private static void findOrCreateHomeDirs() throws IOException
    {
        var dirNames = new String[]{
              CHARACTERS_DIR,
              AUDIO_DIR
        };

        for(var dirName : dirNames)
        {
            var dir = new File(ROOT_DIR + dirName);
            // create if it doesn't exist yet
            if(!dir.exists())
            {
                System.out.println("Directory \"" + ROOT_DIR + dirName + "\" not found, creating new one");

                if(dir.mkdirs())
                {
                    //copy sample files into new folders
                    var resourceFolder = new File(App.class.getResource(dirName).getFile());
                    var files = resourceFolder.listFiles();
                    for(var file : files)
                    {
                        var originalPath = file.toPath();
                        var newPath = Paths.get(ROOT_DIR + dirName + File.separator + file.getName());
                        System.out.println("Copying file \"" + originalPath.toString() + "\" to \"" + newPath + "\"");
                        Files.copy(originalPath, newPath);
                    }
                }
                else
                {
                    throw new IOException("Directory creation failed");
                }
            }

            // make sure it's a directory
            if(!dir.isDirectory())
            {
                throw new IOException(ROOT_DIR + " is not a directory");
            }
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("/controllers/MainWindow"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

}