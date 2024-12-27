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
    public static String prompt;
    public static ArrayList<Integer> clientOutTime = new ArrayList<>();
    public static ArrayList<String> triggerPrefix = new ArrayList<>();
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
        prompt = config.getString("prompt");
        triggerPrefix = (ArrayList<String>) config.get("triggerPrefix");
        clientOutTime = (ArrayList<Integer>) config.get("clientOutTime");
        FastTextModelPath = ConfigPath + config.getString("FastTextModelPath");
        minScore = config.getDouble("minScore");
        autoCmd = config.getBoolean("autoCmd", false);
        useFastText = config.getBoolean("useFastText", false);
        return true;
    }

    public static boolean saveConfig() {
        Config config = new Config(ConfigPath + "/config.yml", Config.YAML);
        config.set("Language", Language);
        config.set("OpenFormCmd", OpenFormCmd);
        config.set("api_url", api_url);
        config.set("api_key", api_key);
        config.set("maxRequestNum", maxRequestNum);
        config.set("prompt", prompt);
        config.set("triggerPrefix", triggerPrefix);
        config.set("clientOutTime", clientOutTime);
        config.save();
        return true;
    }

}
