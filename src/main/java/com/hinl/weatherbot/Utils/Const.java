package com.hinl.weatherbot.Utils;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by HinL on 9/16/2016.
 */
public class Const {
    public static DateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss zzz yyyy");

    public static final String BOT_USERNAME = "Your Bot username";
    public static final String BOT_TOKEN = "Your Bot Token";

    public static final String CurrentWeather_EN = "http://rss.weather.gov.hk/rss/CurrentWeather.xml";
    public static final String CurrentWeather_TC = "http://rss.weather.gov.hk/rss/CurrentWeather_uc.xml";
    public static final String CurrentWeather_SC = "http://gbrss.weather.gov.hk/rss/CurrentWeather_uc.xml";
    public static final String WeatherWarning_EN = "http://rss.weather.gov.hk/rss/WeatherWarningBulletin.xml";
    public static final String WeatherWarning_TC = "http://rss.weather.gov.hk/rss/WeatherWarningBulletin_uc.xml";
    public static final String WeatherWarning_SC = "http://gbrss.weather.gov.hk/rss/WeatherWarningBulletin_uc.xml";



    public static final String EnglishRet = "Ok";
    public static final String TCRet = "知道了";
    public static final String SCRet = "知道了";
}
