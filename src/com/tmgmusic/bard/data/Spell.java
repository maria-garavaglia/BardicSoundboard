package main.java.com.tmgmusic.bard.data;

import java.io.File;

public class Spell
{
   private String name;
   private File audio;

   public Spell(String name, String audioFilename)
   {
      this.name = name;
      audio = new File(audioFilename);
   }

   public String getName()
   {
      return name;
   }

   public File getAudio()
   {
      return audio;
   }
}
