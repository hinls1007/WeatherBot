package com.hinl.weatherbot.Utils;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by HinL on 9/16/2016.
 */
public class RSSParser {

    URL url;
    public RSSParser(String url){
      try{
          this.url = new URL(url);
      } catch (Exception e){
          e.printStackTrace();
      }
    }

    public RSSParser(URL url){
        this.url = url;
    }

    public String getBody() {
        StringBuilder bodyBuilder = new StringBuilder();
        try {
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            // Reading the feed
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(httpcon));

            java.util.List entries = feed.getEntries();
            Iterator itEntries = entries.iterator();

            while (itEntries.hasNext()) {
                SyndEntry entry = (SyndEntry) itEntries.next();
                String[] arry = entry.getDescription().getValue()
                        .replaceAll("(?s)<[^>]*>(\\s*<[^>]*>)*", " ")
                        .replace(";", "\n")
                        .trim().split("\n");
                for (String s : arry) {
                    bodyBuilder.append(s.trim() + "\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bodyBuilder.toString();
    }

    public Date getPubDate(){
        try {
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            // Reading the feed
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(httpcon));

            java.util.List entries = feed.getEntries();
            Iterator itEntries = entries.iterator();

            while (itEntries.hasNext()) {
                SyndEntry entry = (SyndEntry) itEntries.next();
                return entry.getPublishedDate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date(System.currentTimeMillis());
    }
}
