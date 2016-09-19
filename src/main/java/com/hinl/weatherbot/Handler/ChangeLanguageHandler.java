package com.hinl.weatherbot.Handler;

import com.hinl.weatherbot.Utils.Const;
import com.hinl.weatherbot.Utils.DBHelper;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Created by HinL on 9/17/2016.
 */

/**
 *  Change the user language and update the datebases
 */
public class ChangeLanguageHandler extends BaseCommandHandler{

    private static final String[] commands = {"english", "繁體中文", "简体中文"};

    public String[] getHooks() {
        return commands;
    }

    public String performAction(Update update) {
        return performAction(update.getUpdateId().toString(), update.getMessage().getText());
    }

    public String performAction(String chatId, String message) {
        String ret = Const.EnglishRet;
        if (message.equals(commands[0])) {
            ret = Const.EnglishRet;
            DBHelper.changeLanguage(chatId, DBHelper.English);
        } else if (message.equals(commands[1])) {
            ret = Const.TCRet;
            DBHelper.changeLanguage(chatId, DBHelper.TraditionChinese);
        } else if (message.equals(commands[2])) {
            ret = Const.SCRet;
            DBHelper.changeLanguage(chatId, DBHelper.SimplifyChinese);
        }
        return ret;
    }
}
