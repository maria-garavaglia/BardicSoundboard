package com.tmgmusic.data;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.tmgmusic.json.SpellKeys;

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

   public Spell(JsonObject json)
   {
      name = json.getString(SpellKeys.KEY_NAME);
      String filename = json.getString(SpellKeys.KEY_FILE);
      audio = new File(filename);
   }

   public String getName()
   {
      return name;
   }

   public File getAudio()
   {
      return audio;
   }

   @Override
   public String toString()
   {
      return this.name;
   }
}
