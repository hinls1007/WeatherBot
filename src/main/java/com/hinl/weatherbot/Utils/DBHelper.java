package com.hinl.weatherbot.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HinL on 9/16/2016.
 */


/**
 * For Data base handling
 */
public class DBHelper {
    public static final String English = "en";
    public static final String TraditionChinese = "tc";
    public static final String SimplifyChinese = "sc";

    private static final String SQLITE_CLASS = "org.sqlite.JDBC";
    private static final String DB_NAME = "jdbc:sqlite:Bot.db";


    private static final String Table_CHAT = "Chats";
    private static final String Table_PubDate = "PubDate";
    private static final String Table_SubScribe = "SubScribe";

    private static final String Key_id = "key_id";
    private static final String Chat_id = "chat_id";
    private static final String Language = "language";

    private static final String Url = "Url";
    private static final String PubDate = "pubDate";

    private static final String Topic = "Topic";

    private static final String CREATE_Table_Chat = "CREATE TABLE IF NOT EXISTS " + Table_CHAT + "(" + Key_id + " INTEGER PRIMARY KEY,"
            + Chat_id + " TEXT, "+ Language + " TEXT "+ ")";

    private static final String CREATE_Table_PubDate = "CREATE TABLE IF NOT EXISTS " + Table_PubDate + "(" + Key_id + " INTEGER PRIMARY KEY,"
            + Url + " TEXT, "+ PubDate + " DATE "+ ")";

    private static final String CREATE_Table_SubScribe = "CREATE TABLE IF NOT EXISTS " + Table_SubScribe + "(" + Key_id + " INTEGER PRIMARY KEY,"
            + Chat_id + " TEXT, "+ Topic + " TEXT "+ ")";


    /**
     * prepare different table for data storage
     */

    public static void init(){
        createTable(CREATE_Table_Chat);
        createTable(CREATE_Table_PubDate);
        createTable(CREATE_Table_SubScribe);
    }

    public static void clearTable(){
        createTable("DROP TABLE IF EXISTS " + Table_CHAT);
        createTable("DROP TABLE IF EXISTS " + Table_PubDate);
        createTable("DROP TABLE IF EXISTS " + Table_SubScribe);

        init();
    }

    public static void createTable(String statement){
        Statement stmt = null;
        try {
            Connection c = getConnection();
            stmt = c.createStatement();
            stmt.executeUpdate(statement);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }


    public static Connection getConnection(){

        Connection c = null;
        try {
            Class.forName(SQLITE_CLASS);
            c = DriverManager.getConnection(DB_NAME);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Operation done successfully");
        return c;
    }

    /**
     * Remember the Chat and set the default language for the chats
     * @param chat_id
     */
    public static void addChat(String chat_id){
        try{
            Connection c = getConnection();
            Statement stmt = c.createStatement();
            String q = "SELECT * FROM " + Table_CHAT;
            ResultSet ret = stmt.executeQuery(q);
            if ( !ret.next() ){
                String insert = "INSERT INTO " + Table_CHAT +" ("+ Chat_id +"," + Language+") " + "VALUES ('"+chat_id+ "','" + English+"');";
                System.out.println("i:"+insert);
                stmt.executeUpdate(insert);
            }

            ret.close();
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  Get the chat perference Language
     * @param chat_id
     * @return the Lanuage String
     */
    public static String getLanguage(String chat_id){
        String lang = "";
        try {
            Connection c = getConnection();
            Statement stmt = c.createStatement();
            String q = "SELECT * FROM " + Table_CHAT + " WHERE " + Chat_id + "='" +chat_id + "'";
            ResultSet ret = stmt.executeQuery(q);
            if ( ret.next() ){
                lang = ret.getString(Language);
            }

            ret.close();
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return lang;
    }

    /**
     * Change The perference language wth chat id
     * @param chat_id
     * @param language
     */

    public static void changeLanguage(String chat_id, String language){
        try {
            Connection c = getConnection();
            Statement stmt = c.createStatement();
            String q = "SELECT * FROM " + Table_CHAT + " WHERE " + Chat_id + "='" +chat_id + "'";
            ResultSet ret = stmt.executeQuery(q);
            if ( ret.next() ){
                int key_id = ret.getInt(Key_id);
                String update = "Update " + Table_CHAT +" set " +Language +" ='" + language +"' where " +Key_id +"=" +key_id;
                stmt.executeUpdate(update);
            }

            ret.close();
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  Remember the chat subscript topics
     * @param chatId
     * @param topic
     */

    public static void subScribe(String chatId, String topic){
        try{
            Connection c = getConnection();
            Statement stmt = c.createStatement();
            String q = "SELECT * FROM " + Table_SubScribe + " WHERE "+ Chat_id + "='" +chatId + "' AND " + Topic + "='" + topic + "'";
            ResultSet ret = stmt.executeQuery(q);
            if ( !ret.next() ){
                String insert = "INSERT INTO " + Table_SubScribe +" ("+ Chat_id +"," + Topic+") " + "VALUES ('"+chatId+ "','" + topic+"');";
                stmt.executeUpdate(insert);
            }

            ret.close();
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Delete The record of subscribe
     * @param chatId
     * @param topic
     */
    public static void unSubScribe(String chatId, String topic){
        try{
            Connection c = getConnection();
            Statement stmt = c.createStatement();
            String q = "SELECT * FROM " + Table_SubScribe + " WHERE "+ Chat_id + "='" +chatId + "' AND " + Topic + "='" + topic + "'";
            ResultSet ret = stmt.executeQuery(q);
            if ( ret.next() ){
                int keyId = ret.getInt(Key_id);
                String delete = "DELETE FROM " +Table_SubScribe + " WHERE " + Key_id +"='" + keyId +"'";
                stmt.executeUpdate(delete);
            }

            ret.close();
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  Get The chatId subscript to the topic
     * @param topic
     * @return The List of chatId
     */
    public static List<String> getSubScribe(String topic){
        List<String> ids = new ArrayList<String>();
        try {
            Connection c = getConnection();
            Statement stmt = c.createStatement();
            String q = "SELECT * FROM " + Table_SubScribe + " WHERE " + Topic + "='" +topic +"'" ;
            ResultSet ret = stmt.executeQuery(q);
            while ( ret.next() ){
                ids.add(ret.getString(Chat_id));
            }
            ret.close();
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return ids;
    }

    /**
     *  update the publish date of the url
     * @param url
     * @param date
     */
    public static void updatePubDate(String url, Date date){
        try {
            Connection c = getConnection();
            Statement stmt = c.createStatement();
            String q = "SELECT * FROM " + Table_PubDate + " WHERE " + Url + "='" +url + "'";
            ResultSet ret = stmt.executeQuery(q);
            if ( ret.next() ){
                int key_id = ret.getInt(Key_id);
                String update = "Update " + Table_PubDate +" set " +PubDate +" ='" + date +"' where " +key_id +"=" +key_id;
                stmt.executeUpdate(update);
            } else {
                String insert = "INSERT INTO " + Table_PubDate +" ("+ Url +"," + PubDate+") " + "VALUES ('"+url+ "','" + date+"');";
                stmt.executeUpdate(insert);
            }

            ret.close();
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *  Get the Last publish date of the url
     * @param url
     * @return The Last publish date
     */
    public static Date getLastPubDate(String url){
        Date date = new Date();
        try {
            Connection c = getConnection();
            Statement stmt = c.createStatement();
            String q = "SELECT * FROM " + Table_PubDate + " WHERE " + Url + "='" + url + "'";
            ResultSet ret = stmt.executeQuery(q);
            if ( ret.next() ){
                String d = ret.getString(PubDate);
                date = Const.dateFormat.parse(d);
            }

            ret.close();
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }


}
