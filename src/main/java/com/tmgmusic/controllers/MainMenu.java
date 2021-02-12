package com.tmgmusic.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;

import java.io.IOException;

public class MainMenu extends MenuBar
{
   public MainMenu()
   {
      var loader = new FXMLLoader();
      loader.setRoot(this);
      loader.setControllerFactory(theClass -> this);

      String fileName = this.getClass().getSimpleName() + ".fxml";
      try
      {
         loader.load(this.getClass().getResourceAsStream(fileName));
      }
      catch(IOException e)
      {
         throw new RuntimeException(e);
      }
   }
}
