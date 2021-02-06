package main.java.com.tmgmusic.bard.data;

import java.util.HashMap;
import java.util.Map;

public class Character
{
   private String name;
   private Map<String, Spell> spells;

   public Character()
   {
      name = "John Doe";
      spells = new HashMap<>();
   }

   public Character(String name)
   {
      this.name = name;
      spells = new HashMap<>();
   }

   public String getName()
   {
      return name;
   }

   public Map<String, Spell> getSpells()
   {
      return spells;
   }

   public Spell getSpell(String spellName)
   {
      var spell = spells.get(spellName);
      if(spell == null)
      {
         System.err.println("Error: Spell '" + spellName + "' not found in " + name + "'s spell list");
      }
      return spell;
   }

   public void addSpell(Spell spell)
   {
      spells.put(spell.getName(), spell);
   }
}
