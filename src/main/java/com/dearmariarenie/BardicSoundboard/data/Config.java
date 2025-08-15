package com.dearmariarenie.BardicSoundboard.data;

import com.github.cliftonlabs.json_simple.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config implements Jsonable
{
    private static String configFilename = Path.of(System.getProperty("user.dir"), "config.json").toString();

    public static String charactersDir = Path.of(System.getProperty("user.dir"), "Characters").toString();
    public static String audioDir = Path.of(System.getProperty("user.dir"), "Audio").toString();

    public Config()
    {
        load();
    }

    public void save()
    {
        try
        {
            var file = new File(configFilename);
            if(!file.exists())
            {
                if(!file.createNewFile())
                {
                    System.err.println("Listen, I don't really know what happened here... " +
                        "Anyway, configs weren't saved.");
                    return;
                }
            }

            String newJson = toJson();

            Files.writeString(file.toPath(), newJson);

            System.out.println("Wrote config to '" + configFilename + "'");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }


    }

    public void load()
    {
        try(var reader = new FileReader(configFilename))
        {
            JsonObject json = (JsonObject)Jsoner.deserialize(reader);

            charactersDir = json.getString(ConfigKeys.KEY_CHARDIR);
            audioDir = json.getString(ConfigKeys.KEY_AUDIODIR);
        }
        catch(FileNotFoundException fnf)
        {
            System.out.println("WARNING: Config file '" + configFilename + "' not found, falling back to defaults");
        }
        catch(IOException | JsonException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String toJson()
    {
        final var json = getJsonObject();

        return Jsoner.prettyPrint(json.toJson());
    }

    @Override
    public void toJson(Writer writable) throws IOException
    {
        final var json = getJsonObject();

        json.toJson(writable);
    }

    private JsonObject getJsonObject()
    {
        final JsonObject json = new JsonObject();
        json.put("charactersDir", charactersDir);
        json.put("audioDir", audioDir);

        return json;
    }

    enum ConfigKeys implements JsonKey
    {
        KEY_CHARDIR("charactersDir"),
        KEY_AUDIODIR("audioDir");

        private final Object value;

        /**
         * Instantiates a JsonKey with the provided value.
         *
         * @param value represents a valid default for the key.
         */
        ConfigKeys(final Object value)
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
