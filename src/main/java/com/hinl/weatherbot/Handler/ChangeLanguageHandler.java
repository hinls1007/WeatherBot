package com.hinl.weatherbot.Handler;

import com.hinl.weatherbot.Utils.DBHelper;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Created by HinL on 9/17/2016.
 */
public class ChangeLanguageHandler extends BaseCommandHandler{

    private static final String[] commands = {"english", "繁體中文", "简体中文"};
    private static final String EnglishRet = "Ok";
    private static final String TCRet = "知道了";
    private static final String SCRet = "知道了";

    public String[] getHooks() {
        return commands;
    }

    public String performAction(Update update) {
        String ret = EnglishRet;
        Message message = update.getMessage();
        String input = message.getText();
        String chatId = message.getChatId().toString();
        if (input.equals(commands[0])) {
            ret = EnglishRet;
            DBHelper.changeLanguage(chatId, DBHelper.English);
        } else if (input.equals(commands[1])) {
            ret = TCRet;
            DBHelper.changeLanguage(chatId, DBHelper.TraditionChinese);
        } else if (input.equals(commands[2])) {
            ret = SCRet;
            DBHelper.changeLanguage(chatId, DBHelper.SimplifyChinese);
        }
        return ret;
    }
}
