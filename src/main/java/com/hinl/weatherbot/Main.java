package com.hinl.weatherbot;

import com.hinl.weatherbot.Handler.ChangeLanguageHandler;
import com.hinl.weatherbot.Handler.CurrentWeatherHandler;
import com.hinl.weatherbot.Handler.WarningHandler;
import com.hinl.weatherbot.Utils.CheckUpdateThread;
import com.hinl.weatherbot.Utils.Const;
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
        registerHandler();
        startTrackingThread();
        try {
            telegramBotsApi.registerBot(new BotMessasgeReciever());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Start Tracking Thread for polling update the warning and current weather
     */

    public static void startTrackingThread(){
        CheckUpdateThread.addTracking(Const.CurrentWeather_EN, "current");
        CheckUpdateThread.addTracking(Const.WeatherWarning_EN, "warning");
        CheckUpdateThread thread = new CheckUpdateThread();
        thread.start();
    }

    /**
     * register the command Handler for different command and topics
     */
    public static void registerHandler(){

        BotMessasgeReciever.registerHandler(CurrentWeatherHandler.class);
        BotMessasgeReciever.registerHandler(WarningHandler.class);
        BotMessasgeReciever.registerHiddenHandler(ChangeLanguageHandler.class);
    }
}
