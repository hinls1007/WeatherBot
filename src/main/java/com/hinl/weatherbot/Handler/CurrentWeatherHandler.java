package com.hinl.weatherbot.Handler;

import com.hinl.weatherbot.Utils.Const;
import com.hinl.weatherbot.Utils.RSSParser;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Created by HinL on 9/16/2016.
 */
public class CurrentWeatherHandler extends BaseCommandHandler {

    private static final String[] commands = {"current"};

    public String[] getHooks() {
        return commands;
    }

    public String performAction(Update update) {

        RSSParser parser = new RSSParser(Const.CurrentWeather_EN);
        return parser.getBody();
    }
}
