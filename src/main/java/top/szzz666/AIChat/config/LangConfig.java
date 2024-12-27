package top.szzz666.AIChat.config;

import cn.nukkit.utils.Config;

import static top.szzz666.AIChat.AIChatMain.ConfigPath;
import static top.szzz666.AIChat.AIChatMain.plugin;
import static top.szzz666.AIChat.config.MyConfig.Language;


public class LangConfig {
    public static String broadcastMsg;
    public static String requestFailedMsg;
    public static String requestExcessiveMsg;
    public static String OpFormCmd_description;
    public static String mainForm_title;
    public static String mainForm_content;
    public static String mainForm_button1;
    public static String mainForm_button2;
    public static String mainForm_sendMessage;
    public static String Form0_title;
    public static String Form0_Element0;
    public static String Form0_Element1;
    public static String Form0_Element2;
    public static String Form0_Element3;
    public static String Form0_Element4;
    public static String Form0_Element5;
    public static String Form0_Element6;
    public static String Form0_sendMessage;
    public static String Sec_sendMessage;
    public static String joinMsg;

    public static boolean loadLangConfig() {
        plugin.saveResource("language/chs.yml");
        plugin.saveResource("language/eng.yml");
        Config LangConfig = new Config(ConfigPath + "/language/" + Language, Config.YAML);
        OpFormCmd_description = LangConfig.getString("OpFormCmd_description");
        broadcastMsg = LangConfig.getString("broadcastMsg");
        requestFailedMsg = LangConfig.getString("requestFailedMsg");
        requestExcessiveMsg = LangConfig.getString("requestExcessiveMsg");
        mainForm_title = LangConfig.getString("mainForm_title");
        mainForm_content = LangConfig.getString("mainForm_content");
        mainForm_button1 = LangConfig.getString("mainForm_button1");
        mainForm_button2 = LangConfig.getString("mainForm_button2");
        mainForm_sendMessage = LangConfig.getString("mainForm_sendMessage");
        Form0_title = LangConfig.getString("Form0_title");
        Form0_Element0 = LangConfig.getString("Form0_Element0");
        Form0_Element1 = LangConfig.getString("Form0_Element1");
        Form0_Element2 = LangConfig.getString("Form0_Element2");
        Form0_Element3 = LangConfig.getString("Form0_Element3");
        Form0_Element4 = LangConfig.getString("Form0_Element4");
        Form0_Element5 = LangConfig.getString("Form0_Element5");
        Form0_Element6 = LangConfig.getString("Form0_Element6");
        Form0_sendMessage = LangConfig.getString("Form0_sendMessage");
        Sec_sendMessage = LangConfig.getString("Sec_sendMessage");
        joinMsg = LangConfig.getString("joinMsg");
        return true;
    }

}
