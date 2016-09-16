package com.hinl.weatherbot.Handler;

import com.hinl.weatherbot.Utils.DBHelper;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Created by HinL on 9/16/2016.
 */
public abstract class BaseCommandHandler {

    public static final int Lang_EN = 0x0A;
    public static final int Lang_TC = 0x0B;
    public static final int Lang_SC = 0x0C;

    public int getLanguage(String chatId){
        String lang = DBHelper.getLanguage(chatId);
        if (lang.equals(DBHelper.English)){
            return Lang_EN;
        } else if (lang.equals(DBHelper.TraditionChinese)){
            return Lang_TC;
        } else if (lang.equals(DBHelper.SimplifyChinese)){
            return Lang_SC;
        }
        return 0;
    }

    public abstract String[] getHooks();

    public abstract String performAction(Update update);
}
