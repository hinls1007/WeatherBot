package com.hinl.weatherbot.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HinL on 9/16/2016.
 */
public class DBHelper {
    public static final String English = "en";
    public static final String TraditionChinese = "tc";
    public static final String SimplifyChinese = "sc";

    private static final String SQLITE_CLASS = "org.sqlite.JDBC";
    private static final String DB_NAME = "jdbc:sqlite:Bot.db";


    private static final String Table_CHAT = "Chats";

    private static final String Table_PubDate = "PubDate";

    private static final String Key_id = "key_id";
    private static final String Chat_id = "chat_id";
    private static final String Language = "language";

    private static final String url = "Url";
    private static final String pubDate = "pubDate";

    private static final String CREATE_Table_Chat = "CREATE TABLE IF NOT EXISTS " + Table_CHAT + "(" + Key_id + " INTEGER PRIMARY KEY,"
            + Chat_id + " TEXT, "+ Language + " TEXT "+ ")";

    private static final String CREATE_Table_PubDate = "CREATE TABLE IF NOT EXISTS " + Table_PubDate + "(" + Key_id + " INTEGER PRIMARY KEY,"
            + url + " TEXT, "+ pubDate + " DATE "+ ")";




    public static void init(){
        createTable(CREATE_Table_Chat);
        createTable(CREATE_Table_PubDate);
    }

    public static void clearTable(){
        createTable("DROP TABLE IF EXISTS " + Table_CHAT);
        createTable("DROP TABLE IF EXISTS " + Table_PubDate);

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

    public static String getAllId(){
        try {
            Connection c = getConnection();
            Statement stmt = c.createStatement();
            String q = "SELECT * FROM " + Table_CHAT ;
            ResultSet ret = stmt.executeQuery(q);
            StringBuilder builder = new StringBuilder();
            while ( ret.next() ){
                builder.append(ret.getString(Chat_id) + "\n");
//                return ret.getString(Language);
            }
            ret.close();
            stmt.close();
            c.commit();
            c.close();
            return builder.toString();


        } catch (Exception e){
            e.printStackTrace();
        }
        return "no";
    }


}
