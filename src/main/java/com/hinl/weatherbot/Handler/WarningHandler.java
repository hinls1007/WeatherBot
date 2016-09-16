package com.hinl.weatherbot.Handler;

import com.hinl.weatherbot.Utils.Const;
import com.hinl.weatherbot.Utils.RSSParser;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Created by HinL on 9/17/2016.
 */
public class WarningHandler extends BaseCommandHandler{

    private static final String[] commands = {"warning"};

    public String[] getHooks() {
        return commands;
    }

    public String performAction(Update update) {
        RSSParser parser = new RSSParser(Const.WeatherWarning_EN);
        return parser.getBody();
    }
}
