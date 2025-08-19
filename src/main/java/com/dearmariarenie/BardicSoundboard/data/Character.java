package com.dearmariarenie.BardicSoundboard.data;

import com.github.cliftonlabs.json_simple.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Character implements Jsonable
{
    private String saveFilename;
    private String name;
    private final List<Spell> spells = new ArrayList<>();

    public Character()
    {
        name = "New Character";
    }

    public Character(File savedFile) throws IOException, JsonException
    {
        saveFilename = savedFile.getPath();
        try(var reader = new FileReader(savedFile))
        {
            JsonObject json = (JsonObject)Jsoner.deserialize(reader);

            name = json.getString(CharacterKeys.KEY_CHARNAME);
            JsonArray spellsJson = json.getCollection(CharacterKeys.KEY_SPELLS);
            for(int index = 0; index < spellsJson.size(); index++)
            {
                var newSpell = new Spell(spellsJson.getMap(index));
                spells.add(newSpell);
            }
        }

    }

    public String getSaveFile()
    {
        return saveFilename;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Spell> getSpells()
    {
        return spells;
    }

    public void addSpell(Spell newSpell)
    {
        spells.add(newSpell);
    }

    public void removeSpell(Spell toRemove)
    {
        for(var spell : spells)
        {
            if(spell.equals(toRemove))
            {
                spells.remove(spell);
                break;
            }
        }
    }

    /****************************************************************************
     * To/from JSON
     */

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

    enum CharacterKeys implements JsonKey
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
}


