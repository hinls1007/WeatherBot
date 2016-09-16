package com.hinl.weatherbot.Utils;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by HinL on 9/16/2016.
 */
public class Const {
    public static DateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z");

    public static final String BOT_USERNAME = "HinlWeatherbot";
    public static final String BOT_TOKEN = "275674234:AAEFc613KLVJl8EHfOzqFGRHR58_RrOh4BU";

    public static final String CurrentWeather_EN = "http://rss.weather.gov.hk/rss/CurrentWeather.xml";
    public static final String CurrentWeather_TC = "http://rss.weather.gov.hk/rss/CurrentWeather_uc.xml";
    public static final String CurrentWeather_SC = "http://gbrss.weather.gov.hk/rss/CurrentWeather_uc.xml";
    public static final String WeatherWarning_EN = "http://rss.weather.gov.hk/rss/WeatherWarningBulletin.xml";
    public static final String WeatherWarning_TC = "http://rss.weather.gov.hk/rss/WeatherWarningBulletin_uc.xml";
    public static final String WeatherWarning_SC = "http://gbrss.weather.gov.hk/rss/WeatherWarningBulletin_uc.xml";
}
