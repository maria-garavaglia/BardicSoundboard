package com.tmgmusic.data;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsonable;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.tmgmusic.json.CharacterKeys;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Character implements Jsonable
{
   private String saveFilename;
   private String name;
   private List<Spell> spells = new ArrayList<>();

   public Character()
   {
      name = "New Character";
   }

   public Character(JsonObject json)
   {
      name = json.getString(CharacterKeys.KEY_CHARNAME);
      JsonArray spellsJson = json.getCollection(CharacterKeys.KEY_SPELLS);
      for(int index = 0; index < spellsJson.size(); index++)
      {
         var newSpell = new Spell(spellsJson.getMap(index));
         spells.add(newSpell);
      }
   }

   public String getName()
   {
      return name;
   }

   public List<Spell> getSpells()
   {
      return spells;
   }

//   public Spell getSpell(String spellName)
//   {
//      var spell = spells.get(spellName);
//      if(spell == null)
//      {
//         System.err.println("Error: Spell '" + spellName + "' not found in " + name + "'s spell list");
//      }
//      return spell;
//   }

//   public void addSpell(Spell spell)
//   {
//      spells.put(spell.getName(), spell);
//   }

   private JsonObject getJsonObject()
   {
      final JsonObject json = new JsonObject();
      json.put("charName", name);
      json.put("spells", spells);
      return json;
   }

   @Override
   public String toJson()
   {
      final JsonObject json = getJsonObject();

      return Jsoner.prettyPrint(json.toJson());
   }

   @Override
   public void toJson(Writer writable) throws IOException
   {
      final JsonObject json = getJsonObject();

      json.toJson(writable);
   }

}
