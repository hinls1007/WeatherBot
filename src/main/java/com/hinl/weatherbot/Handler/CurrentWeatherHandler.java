package com.hinl.weatherbot.Handler;

import com.hinl.weatherbot.Utils.Const;
import com.hinl.weatherbot.Utils.RSSParser;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Created by HinL on 9/16/2016.
 */

/**
 * Reutrn the current Weather String from RSS
 */
public class CurrentWeatherHandler extends BaseCommandHandler {

    private static final String[] commands = {"current"};

    public String[] getHooks() {
        return commands;
    }

    public String performAction(Update update) {
        return performAction(update.getUpdateId().toString(), update.getMessage().getText());
    }

    public String performAction(String chatId, String message) {
        String url = Const.CurrentWeather_EN;
        switch (getLanguage(chatId)){
            case Lang_EN:
                url = Const.CurrentWeather_EN;
                break;
            case Lang_TC:
                url = Const.CurrentWeather_TC;
                break;
            case Lang_SC:
                url = Const.CurrentWeather_SC;
                break;
        }
        RSSParser parser = new RSSParser(url);
        return parser.getBody();
    }
}
