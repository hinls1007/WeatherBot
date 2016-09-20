# WeatherBot

This is a Java bases Telegram weather bot. It can tell the current weather detail and the weather warning after added the bot in the Telegram.

How to use the Telegram bot
-------------

After added the Telegram bot, you can use the follow command:

    1. topics (Tell you what kind of topic you can get and subscribe)
    2. tellme [topic] (Tell the current topic content)
    3. subscribe [topic] (Subscribe the topic. While the content have updated, it will send the updated content to your chat)
    4. unsubscribe [topic] (UnSubscribe the topic. You will not receive the content although there have update)


How to use the Java code
-------------

The Main.java in the package 'com.hinl.weatherbot' already have all ingredient you need to compile the Telegram bot

The only place you need to modify is the Const.java in package 'com.hinl.weatherbot.Utils'.

```java
    public static final String BOT_USERNAME = "[Telegram Bot Username]";
    public static final String BOT_TOKEN = "[Telegram Bot Token]";
```

Username and the token is get From the Telegram BotFather (https://telegram.me/botfather). You can create your own bot in BotFather and get the username and token

