package com.tmgmusic.data;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.tmgmusic.json.CharacterKeys;

import java.util.HashMap;
import java.util.Map;

public class Character
{
//   private final String name;
   private final Map<String, Spell> spells = new HashMap<>();

//   public Character()
//   {
//      name = "John Doe";
//   }

//   public Character(String name)
//   {
//      this.name = name;
//   }

   public Character(JsonObject json)
   {
//      name = json.getString(CharacterKeys.KEY_CHARNAME);
      JsonArray spellsJson = json.getCollection(CharacterKeys.KEY_SPELLS);
      for(int index = 0; index < spellsJson.size(); index++)
      {
         var newSpell = new Spell(spellsJson.getMap(index));
         spells.put(newSpell.getName(), newSpell);
      }
   }

//   public String getName()
//   {
//      return name;
//   }

   public Map<String, Spell> getSpells()
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
}
