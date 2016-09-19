package com.hinl.weatherbot.Handler;

import com.hinl.weatherbot.Utils.DBHelper;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Created by HinL on 9/17/2016.
 */

/**
 * Let the user subscribe and unsbscribe he topic, and storage to database for the CheckUpdateThread to use
 */
public class SubscribeHandler extends BaseCommandHandler{

    private static final int SubType = 0x0A;
    private static final int UnSubType = 0x0B;

    public static final String[] commands = {"subscribe ","unsubscribe "};

    public static boolean isSubScription(String command){
        for (String s : commands){
            if (command.startsWith(s)){
                return true;
            }
        }
        return false;
    }

    public String[] getHooks() {
        return commands;
    }

    public String performAction(Update update) {
        return performAction(update.getUpdateId().toString(), update.getMessage().getText());
    }

    public String performAction(String chatId, String message) {
        String initial = "";
        int type = SubType;
        if (message.startsWith(commands[0])){
            initial = commands[0];
            type = SubType;
        } else if (message.startsWith(commands[1])){
            initial = commands[1];
            type = UnSubType;
        }
        String topic = message.substring(initial.length());
        switch (type){
            case SubType:
                DBHelper.subScribe(chatId, topic);
                break;
            case UnSubType:
                DBHelper.unSubScribe(chatId, topic);
                break;
            default:
                return "error";
        }

        return getLangRet(chatId);
    }
}
