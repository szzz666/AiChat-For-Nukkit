package top.szzz666.AIChat.config;

import cn.nukkit.utils.Config;

import java.util.ArrayList;

import static top.szzz666.AIChat.AIChatMain.ConfigPath;
import static top.szzz666.AIChat.AIChatMain.plugin;

public class MyConfig {
    public static String Language;
    public static String OpenFormCmd;
    public static String api_url;
    public static String api_key;
    public static int maxRequestNum;
    public static int maxContextNum = 30;
    public static int maxCacheNum = 200;
    public static String prompt;
    public static ArrayList<Integer> clientOutTime = new ArrayList<>();
    public static ArrayList<String> resetCommands = new ArrayList<>();
    public static ArrayList<String> triggerPrefix = new ArrayList<>();
    public static ArrayList<String> blurTriggerWords = new ArrayList<>();
    public static String FastTextModelPath;
    public static double minScore;
    public static boolean autoCmd;
    public static boolean useFastText;

    public static boolean loadConfig() {
        plugin.saveResource("config.yml");
        plugin.saveResource("requestJson.json");
        plugin.saveResource("prompt.txt");
        Config config = new Config(ConfigPath + "/config.yml", Config.YAML);
        Language = config.getString("Language");
        OpenFormCmd = config.getString("OpenFormCmd");
        api_url = config.getString("api_url");
        api_key = config.getString("api_key");
        maxRequestNum = config.getInt("maxRequestNum");
        maxContextNum = config.getInt("maxContextNum", 30);
        maxCacheNum = config.getInt("maxCacheNum", 200);
        prompt = config.getString("prompt", "prompt.txt");
        triggerPrefix = (ArrayList<String>) config.getStringList("triggerPrefix");
        resetCommands = (ArrayList<String>) config.getStringList("resetCommands");
        blurTriggerWords = (ArrayList<String>) config.getStringList("blurTriggerWords");
        clientOutTime = (ArrayList<Integer>) config.get("clientOutTime");
        FastTextModelPath = ConfigPath + config.getString("FastTextModelPath");
        minScore = config.getDouble("minScore", 0.94);
        autoCmd = config.getBoolean("autoCmd", false);
        useFastText = config.getBoolean("useFastText", false);
        saveConfig();
        return true;
    }

    public static boolean saveConfig() {
        Config config = new Config(ConfigPath + "/config.yml", Config.YAML);
        config.set("Language", Language);
        config.set("OpenFormCmd", OpenFormCmd);
        config.set("api_url", api_url);
        config.set("api_key", api_key);
        config.set("maxRequestNum", maxRequestNum);
        config.set("maxContextNum", maxContextNum);
        config.set("maxCacheNum", maxCacheNum);
        config.set("prompt", prompt);
        config.set("resetCommands", resetCommands);
        config.set("blurTriggerWords", blurTriggerWords);
        config.set("triggerPrefix", triggerPrefix);
        config.set("clientOutTime", clientOutTime);
        config.set("FastTextModelPath", FastTextModelPath);
        config.set("minScore", minScore);
        config.set("autoCmd", autoCmd);
        config.set("useFastText", useFastText);
        config.save();
        return true;
    }

}
