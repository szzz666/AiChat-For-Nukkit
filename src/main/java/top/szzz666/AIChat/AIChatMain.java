package top.szzz666.AIChat;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import top.szzz666.AIChat.command.MyCommand;
import top.szzz666.AIChat.entity.Message;
import top.szzz666.AIChat.event.Listeners;
import top.szzz666.AIChat.fast_text.FastText;
import top.szzz666.AIChat.tools.LimitedSizeHashMap;

import java.util.ArrayList;
import java.util.HashMap;

import static top.szzz666.AIChat.config.LangConfig.loadLangConfig;
import static top.szzz666.AIChat.config.MyConfig.loadConfig;
import static top.szzz666.AIChat.config.MyConfig.maxCacheNum;
import static top.szzz666.AIChat.config.secData.loadSecData;
import static top.szzz666.AIChat.tools.pluginUtil.lineConsole;
import static top.szzz666.AIChat.tools.pluginUtil.nkConsole;


public class AIChatMain extends PluginBase {
    public static Plugin plugin;
    public static Server nkServer;
    public static CommandSender consoleObjects;
    public static String ConfigPath;
    public static FastText fastText;
    public static LimitedSizeHashMap<String, ArrayList<Message>> playerChat;

    @Override
    public void onLoad() {
        //插件读取
        nkServer = getServer();
        plugin = this;
        consoleObjects = getServer().getConsoleSender();
        ConfigPath = getDataFolder().getPath();
        loadConfig();
        loadLangConfig();
        loadSecData();
        playerChat = new LimitedSizeHashMap<>(maxCacheNum);

//        fastText = new FastText();
        lineConsole("   _   _  ___ _         _   \n" +
                "  /_\\ (_)/ __| |_  __ _| |_ \n" +
                " / _ \\| | (__| ' \\/ _` |  _|\n" +
                "/_/ \\_\\_|\\___|_||_\\__,_|\\__|");
        nkConsole("&bAiChat插件读取...");
    }

    @Override
    public void onEnable() {
        //注册监听器
        this.getServer().getPluginManager().registerEvents(new Listeners(), this);
        //注册命令
        this.getServer().getCommandMap().register(this.getName(), new MyCommand());
        nkConsole("&bAiChat插件开启");
        nkConsole("&c如果遇到任何bug，请加入Q群进行反馈：894279534", 2);
    }

    @Override
    public void onDisable() {
        //插件关闭
        nkConsole("&bAiChat插件关闭");
    }


}
