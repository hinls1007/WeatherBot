package com.hinl.weatherbot.receiver;

import com.hinl.weatherbot.Handler.BaseCommandHandler;
import com.hinl.weatherbot.Utils.Const;
import com.hinl.weatherbot.Utils.DBHelper;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by HinL on 9/16/2016.
 */
public class BotMessasgeReciever extends TelegramLongPollingBot {


    private static final String TopicCommand = "topics";

    static HashMap<String, String> actionHandlerMapper = new HashMap<String, String>();

    public static String getCommands(){
        List<String> commands = new ArrayList<String>(actionHandlerMapper.keySet());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < commands.size(); i++){
            builder.append(commands.get(i));
            if (i != commands.size()-1){
                builder.append(" ,");
            }
        }
        return builder.toString();
    }

    public static void clearHandler(){
        if (actionHandlerMapper!=null) {
            actionHandlerMapper.clear();
        }
    }

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

    public static BaseCommandHandler getHandler(String action){
        String actionHandlerClassName = actionHandlerMapper.get(action);

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

    private static BaseCommandHandler getHandler(Class<? extends BaseCommandHandler> hClass){
        BaseCommandHandler commandHandler = null;
        try {
            commandHandler = hClass.getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commandHandler;
    }


    public void onUpdateReceived(Update update) {

        if(update.hasMessage()) {
            Message message = update.getMessage();

            //check if the message has text. it could also contain for example a location ( message.hasLocation() )
            if (message.hasText()) {
                String msg = message.getText();
                if (msg.trim().equals("/start")) {
                    DBHelper.addChat(message.getChatId().toString());
                }else if (msg.trim().equals(TopicCommand)){
                    SendMessage sendMessageRequest = new SendMessage();
                    sendMessageRequest.setChatId(message.getChatId().toString());
                    sendMessageRequest.setText(getCommands());
                    sendMsg(sendMessageRequest);
                } else if (msg.equals("Admin clear")) {
                    DBHelper.clearTable();
                } else {
                    BaseCommandHandler handler = getHandler(msg.trim());
                    SendMessage sendMessageRequest = new SendMessage();
                    sendMessageRequest.setChatId(message.getChatId().toString());
                    if (handler == null){
                        sendMessageRequest.setText("error");
                    } else {
                        String ret = handler.performAction(update);
                        sendMessageRequest.setText(ret);
                    }
                    sendMsg(sendMessageRequest);
                }
                System.out.println("ids:"+DBHelper.getAllId());
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
