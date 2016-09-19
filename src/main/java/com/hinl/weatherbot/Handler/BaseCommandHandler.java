package com.hinl.weatherbot.Handler;

import com.hinl.weatherbot.Utils.Const;
import com.hinl.weatherbot.Utils.DBHelper;
import org.telegram.telegrambots.api.objects.Update;

/**
 * Created by HinL on 9/16/2016.
 */
public abstract class BaseCommandHandler {

    public static final int Lang_EN = 0x0A;
    public static final int Lang_TC = 0x0B;
    public static final int Lang_SC = 0x0C;

    /**
     *  return different language respond after the chatId send commands
     * @param chatId
     * @return String Const fot different language respond
     */

    public String getLangRet(String chatId){
        String lang = DBHelper.getLanguage(chatId);
        if (lang.equals(DBHelper.English)){
            return Const.EnglishRet;
        } else if (lang.equals(DBHelper.TraditionChinese)){
            return Const.TCRet;
        } else if (lang.equals(DBHelper.SimplifyChinese)){
            return Const.SCRet;
        }
        return Const.EnglishRet;
    }

    /**
     *  Get The Language const int for switch case
     * @param chatId
     * @return the language const for that chatId user
     */

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

    /**
     *  Abstrate method to getting the command hooked by the Handler
     * @return String[] of the command registration
     */
    public abstract String[] getHooks();

    /**
     *  PerformAction by different Handler with the Update object
     * @param update
     * @return The result to return to chat
     */

    public abstract String performAction(Update update);

    /**
     *  PerformAction by different Handler with chatId and the message received
     * @param chatId the chatId of the message received
     * @param message the message sent by the chatId
     * @return The result to return to chat
     */
    public abstract String performAction(String chatId, String message);
}
