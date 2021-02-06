package com.tmgmusic.bard.json;

import com.github.cliftonlabs.json_simple.JsonKey;

public enum CharacterKeys implements JsonKey
{
   KEY_CHARNAME("charName"),
   KEY_SPELLS("spells");

   private final Object value;

   /**
    * Instantiates a JsonKey with the provided value.
    *
    * @param value represents a valid default for the key.
    */
   CharacterKeys(final Object value)
   {
      this.value = value;
   }

   @Override
   public String getKey()
   {
      return this.value.toString();
   }

   @Override
   public Object getValue()
   {
      return this.value;
   }
}
