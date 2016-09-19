package com.hinl.weatherbot.receiver;

import com.hinl.weatherbot.Handler.BaseCommandHandler;
import com.hinl.weatherbot.Handler.SubscribeHandler;
import com.hinl.weatherbot.Utils.Const;
import com.hinl.weatherbot.Utils.DBHelper;
import org.apache.http.util.TextUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by HinL on 9/16/2016.
 */

/**
 * For handling different message received
 */
public class BotMessasgeReciever extends TelegramLongPollingBot {


    private static final String TopicCommand = "topics";

    private static final String TellMe = "tellme ";

    static HashMap<String, String> actionHandlerMapper = new HashMap<String, String>();
    static HashMap<String, String> hiddenActionHandlerMapper = new HashMap<String, String>();

    /**
     *  Get the topicList which register to the handler
     * @return String LIst of Topics
     */
    public static List<String> getTopicsList(){
        List<String> commands = new ArrayList<String>(actionHandlerMapper.keySet());
        return commands;
    }

    /**
     * Get the topicList which register to the handler
     * @return appended String of topic
     */

    public static String getTopics(){
        List<String> commands = getTopicsList();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < commands.size(); i++){
            builder.append(commands.get(i));
            if (i != commands.size()-1){
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    public static void clearHandler(){
        if (actionHandlerMapper!=null) {
            actionHandlerMapper.clear();
        }
    }

    /**
     * Register different handler for topic handling
     * @param handlerClass
     */
    public static void registerHandler(Class<? extends BaseCommandHandler> handlerClass){
        try {
            String[] actions = handlerClass.getConstructor().newInstance().getHooks();
            for(String action : actions){
                actionHandlerMapper.put(action, handlerClass.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Register different handler for command handling
     * @param handlerClass
     */
    public static void registerHiddenHandler(Class<? extends BaseCommandHandler> handlerClass){
        try {
            String[] actions = handlerClass.getConstructor().newInstance().getHooks();
            for(String action : actions){
                hiddenActionHandlerMapper.put(action, handlerClass.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *  Get the BaseCOmmandHandler From different command or topics
     * @param action
     * @return BaseCommandHandler for different action handling
     */

    public static BaseCommandHandler getHandler(String action){
        String actionHandlerClassName = actionHandlerMapper.get(action);
        if (TextUtils.isEmpty(actionHandlerClassName)){
            actionHandlerClassName = hiddenActionHandlerMapper.get(action);
        }

        Class handlerClass = null;
        BaseCommandHandler commandHandler = null;
        try {
            handlerClass = Class.forName(actionHandlerClassName);

        } catch (Exception e){
            e.printStackTrace();
        }
        if(handlerClass !=null && BaseCommandHandler.class.isAssignableFrom(handlerClass)) {
            try {
                commandHandler = getHandler(handlerClass);
            } catch (Exception e) {

            }
        }
        return commandHandler;
    }

//    public static BaseActionHandler getHandler(PushObject pushObject){
//        return getHandler(pushObject.action);
//    }

    /**
     *  Create the instance of BaseCOmmandHandler
     * @param hClass
     * @return BaseCommandHandler for different action handling
     */

    private static BaseCommandHandler getHandler(Class<? extends BaseCommandHandler> hClass){
        BaseCommandHandler commandHandler = null;
        try {
            commandHandler = hClass.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commandHandler;
    }


    /**
     *  The method called by the long polling job of telegram API
     * @param update
     */
    public void onUpdateReceived(Update update) {

        if(update.hasMessage()) {
            Message message = update.getMessage();

            //check if the message has text. it could also contain for example a location ( message.hasLocation() )
            if (message.hasText()) {

                String chatId = message.getChatId().toString();
                String msg = message.getText();
                if (msg.trim().equals("/start")) {
                    DBHelper.addChat(chatId);
                }else if (msg.trim().equals(TopicCommand)){
                    SendMessage sendMessageRequest = new SendMessage();
                    sendMessageRequest.setChatId(message.getChatId().toString());
                    sendMessageRequest.setText(getTopics());
                    sendMsg(sendMessageRequest);
                } else if (msg.equals("Admin clear")) {
                    DBHelper.clearTable();
                } else if (msg.startsWith(TellMe)){
                    String submsg = msg.substring(TellMe.length());
                    BaseCommandHandler handler = getHandler(submsg.trim());
                    SendMessage sendMessageRequest = new SendMessage();
//                    if (TextUtils.isEmpty(chatId)){
//                        chatId = update.g
//                    }
                    sendMessageRequest.setChatId(chatId);
                    if (handler == null){
                        sendMessageRequest.setText("error");
                    } else {
                        String ret = handler.performAction(chatId, submsg);
                        sendMessageRequest.setText(ret);
                    }
                    sendMsg(sendMessageRequest);
                } else if (SubscribeHandler.isSubScription(msg)) {
                    String ret = new SubscribeHandler().performAction(update);
                    SendMessage sendMessageRequest = new SendMessage();
                    sendMessageRequest.setChatId(chatId);
                    sendMessageRequest.setText(ret);
                } else {
                    BaseCommandHandler handler = getHandler(msg.trim());
                    SendMessage sendMessageRequest = new SendMessage();
                    sendMessageRequest.setChatId(chatId);
                    if (handler == null){
                        sendMessageRequest.setText("error");
                    } else {
                        String ret = handler.performAction(update);
                        sendMessageRequest.setText(ret);
                    }
                    sendMsg(sendMessageRequest);
                }
            }
        }

    }

    public void sendMsg(SendMessage sendMessageRequest){
        try {
            sendMessage(sendMessageRequest); //at the end, so some magic and send the message ;)
        } catch (Exception e) {
            //do some error handling
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return Const.BOT_USERNAME;
    }

    public String getBotToken() {
        return Const.BOT_TOKEN;
    }
}
