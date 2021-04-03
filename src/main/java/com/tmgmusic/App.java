package com.tmgmusic;

import com.tmgmusic.data.Config;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * JavaFX App
 */
public class App extends Application
{
    public static Config config;


    public static void main(String[] args)// throws IOException
    {
        config = new Config();

//        findOrCreateHomeDirs();

        //Launch FX
        launch();
    }

//    private static void findOrCreateHomeDirs() throws IOException
//    {
//        var dirNames = new String[]{
//            CHARACTERS_DIR,
//            AUDIO_DIR
//        };
//
//        for(var dirName : dirNames)
//        {
//            var dir = new File(ROOT_DIR + dirName);
//            // create if it doesn't exist yet
//            if(!dir.exists())
//            {
//                System.out.println("Directory \"" + ROOT_DIR + dirName + "\" not found, creating new one");
//
//                if(dir.mkdirs())
//                {
//                    //Get all resource files in the given directory
//                    //Trying to get the whole folder as a single resource just returns null, so we have to use reflection instead
//                    ScanResult scan = new ClassGraph()
//                        .acceptPaths(dirName)
//                        .scan();
//                    var files = scan.getAllResources().nonClassFilesOnly();
//                    System.out.println("Found " + files.size() + " items");
//
//                    for(var file : files)
//                    {
//                        //get path name, removing the classpath tacked on the front
//                        var classpath = file.getClasspathElementURI().getPath();
//                        var filePath = file.getURI().getPath().replaceAll(classpath, "");
//
//                        //make a new path ROOT_DIR/dirName/filename.xyz to copy the file to
//                        var filename = Paths.get(filePath).getFileName().toString();
//                        var newPath = Paths.get(ROOT_DIR + dirName + File.separator + filename);
//
//                        //Copy the file
//                        System.out.println("Copying file \"" + filePath + "\" to \"" + newPath + "\"");
//                        Files.copy(App.class.getResourceAsStream(filePath), newPath);
//                    }
//                }
//                else
//                {
//                    throw new IOException("Directory creation failed");
//                }
//            }
//
//            // make sure it's a directory
//            if(!dir.isDirectory())
//            {
//                throw new IOException(ROOT_DIR + " is not a directory");
//            }
//        }
//    }

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("controllers/MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setScene(scene);
        stage.show();
    }
}