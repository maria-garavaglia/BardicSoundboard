package com.tmgmusic.bard.json;

import com.github.cliftonlabs.json_simple.JsonKey;

public enum SpellKeys implements JsonKey
{
   KEY_NAME("name"),
   KEY_FILE("file");

   private final Object value;

   /**
    * Instantiates a JsonKey with the provided value.
    *
    * @param value represents a valid default for the key.
    */
   SpellKeys(final Object value)
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
