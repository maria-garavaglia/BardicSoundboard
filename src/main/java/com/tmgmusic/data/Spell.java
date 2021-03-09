package com.tmgmusic.data;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.tmgmusic.json.SpellKeys;

import java.io.IOException;
import java.io.Writer;

public class Spell implements Jsonable
{
   private final String name;
   private final String audio;

//   public Spell(String name, String audioFilename)
//   {
//      this.name = name;
//      this.audio = "";
//   }

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

   private JsonObject getJsonObject()
   {
      final JsonObject json = new JsonObject();
      json.put("name", name);
      json.put("file", audio);
      return json;
   }

   @Override
   public String toJson()
   {
      final JsonObject json = getJsonObject();

      return json.toJson();
   }

   @Override
   public void toJson(Writer writable) throws IOException
   {
      final JsonObject json = getJsonObject();

      json.toJson(writable);
   }
}
