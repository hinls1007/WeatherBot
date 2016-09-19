package com.hinl.weatherbot.Utils;

import com.hinl.weatherbot.Handler.BaseCommandHandler;
import com.hinl.weatherbot.receiver.BotMessasgeReciever;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by HinL on 9/17/2016.
 */


/**
 *  CheckUpdateThread is for check the RSS have updated  after period of time
 */
public class CheckUpdateThread extends Thread{

    static HashMap<String, String> trackList = new HashMap<String, String>();

    private static final String endpoint = "https://api.telegram.org/";

    private static final long UpdatePeriod = 5 * 60 * 1000;

    public static void addTracking(String url, String topic){
        trackList.put(url, topic);
    }


    public static  HttpResponse<JsonNode> sendMessage(String chatId, String text) throws UnirestException {
        return Unirest.post(endpoint + Const.BOT_TOKEN + "/sendMessage")
                .field("chat_id", chatId)
                .field("text", text)
                .asJson();
    }

    public static  HttpResponse<JsonNode> sendMessage(Integer chatId, String text) throws UnirestException {
        return Unirest.post(endpoint + Const.BOT_TOKEN + "/sendMessage")
                .field("chat_id", chatId)
                .field("text", text)
                .asJson();
    }

    public static HttpResponse<JsonNode> getUpdates(Integer offset) throws UnirestException {
        return Unirest.post(endpoint + Const.BOT_TOKEN + "/getUpdates")
                .field("offset", offset)
                .asJson();
    }


    @Override
    public void run() {

        while (true) {

            List<String> urlList = new ArrayList<String>(trackList.keySet());
            for (String url : urlList) {
                RSSParser parser = new RSSParser(url);
                Date pubDate = parser.getPubDate();
                Date last = DBHelper.getLastPubDate(url);
                if (last.before(pubDate)) {
                    DBHelper.updatePubDate(url, pubDate);
                    String topic = trackList.get(url);
                    List<String> idList = DBHelper.getSubScribe(topic);
                    BaseCommandHandler handler = null;
                    try {
                        handler = BotMessasgeReciever.getHandler(topic);
                        for (String id : idList) {
                            String s = handler.performAction(id, "");
                            sendMessage(id, s);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                Thread.sleep(UpdatePeriod);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
