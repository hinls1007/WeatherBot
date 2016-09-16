package com.hinl.weatherbot.Handler;

import org.telegram.telegrambots.api.objects.Update;

/**
 * Created by HinL on 9/16/2016.
 */
public abstract class BaseCommandHandler {

    public abstract String[] getHooks();

    public abstract String performAction(Update update);
}
