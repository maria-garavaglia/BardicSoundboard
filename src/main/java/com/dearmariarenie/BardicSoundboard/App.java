package com.dearmariarenie.BardicSoundboard;

import com.dearmariarenie.BardicSoundboard.data.Config;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application
{
    public static Config config;


    public static void main(String[] args)
    {
        config = new Config();

        //Launch FX
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("controllers/MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setScene(scene);
        stage.show();
    }
}