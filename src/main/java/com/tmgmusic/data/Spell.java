package com.tmgmusic.data;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.tmgmusic.json.SpellKeys;

import java.io.File;

public class Spell
{
   private String name;
   private String audio;

   public Spell(String name, String audioFilename)
   {
      this.name = name;
      this.audio = "";
   }

   public Spell(JsonObject json)
   {
      name = json.getString(SpellKeys.KEY_NAME);
      audio = json.getString(SpellKeys.KEY_FILE);
   }

   public String getName()
   {
      return name;
   }

   public String getAudio()
   {
      return audio;
   }

   @Override
   public String toString()
   {
      return this.name;
   }
}
