package com.tmgmusic.bard.data;

import java.net.URL;

public class Song
{
   private final String name;
   private final URL url;

   public Song(String name, URL url)
   {
      this.name = name;
      this.url = url;
   }

   public String getName()
   {
      return name;
   }

   public URL getUrl()
   {
      return url;
   }

   @Override
   public String toString()
   {
      return this.name;
   }
}
