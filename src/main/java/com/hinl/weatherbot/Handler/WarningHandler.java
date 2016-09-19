package com.hinl.weatherbot.Handler;

import com.hinl.weatherbot.Utils.Const;
import com.hinl.weatherbot.Utils.RSSParser;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Created by HinL on 9/17/2016.
 */

/**
 * Reutrn the current Weather WarningString from RSS
 */
public class WarningHandler extends BaseCommandHandler{

    private static final String[] commands = {"warning"};

    public String[] getHooks() {
        return commands;
    }

    public String performAction(Update update) {
        return performAction(update.getUpdateId().toString(), update.getMessage().getText());
    }

    public String performAction(String chatId, String message) {

        String url = Const.WeatherWarning_EN;
        switch (getLanguage(chatId)){
            case Lang_EN:
                url = Const.WeatherWarning_EN;
                break;
            case Lang_TC:
                url = Const.WeatherWarning_TC;
                break;
            case Lang_SC:
                url = Const.WeatherWarning_SC;
                break;
        }
        RSSParser parser = new RSSParser(url);
        return parser.getBody();
    }
}
