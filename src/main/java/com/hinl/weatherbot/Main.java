package com.hinl.weatherbot;

import com.hinl.weatherbot.Handler.CurrentWeatherHandler;
import com.hinl.weatherbot.Handler.WarningHandler;
import com.hinl.weatherbot.Utils.DBHelper;
import com.hinl.weatherbot.receiver.BotMessasgeReciever;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

/**
 * Created by HinL on 9/16/2016.
 */
public class Main {
    public static void main(String[] args) {

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        DBHelper.init();
        BotMessasgeReciever.registerHandler(CurrentWeatherHandler.class);
        BotMessasgeReciever.registerHandler(WarningHandler.class);
        try {
            telegramBotsApi.registerBot(new BotMessasgeReciever());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
