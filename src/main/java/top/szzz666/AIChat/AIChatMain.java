package top.szzz666.AIChat;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import top.szzz666.AIChat.command.MyCommand;
import top.szzz666.AIChat.entity.Message;
import top.szzz666.AIChat.event.Listeners;

import java.util.ArrayList;
import java.util.HashMap;

import static top.szzz666.AIChat.config.LangConfig.loadLangConfig;
import static top.szzz666.AIChat.config.MyConfig.loadConfig;
import static top.szzz666.AIChat.tools.pluginUtil.nkConsole;


public class AIChatMain extends PluginBase {
    public static Plugin plugin;
    public static Server nkServer;
    public static CommandSender consoleObjects;
    public static String ConfigPath;
    public static HashMap<Player, ArrayList<Message>> playerChat = new HashMap<>();
    @Override
    public void onLoad(){
        //插件读取
        nkServer = getServer();
        plugin = this;
        consoleObjects = getServer().getConsoleSender();
        ConfigPath = getDataFolder().getPath();
        loadConfig();
        loadLangConfig();
        nkConsole("&b插件读取...");
    }

    @Override
    public void onEnable(){
        //注册监听器
        this.getServer().getPluginManager().registerEvents(new Listeners(), this);
        //注册命令
        this.getServer().getCommandMap().register(this.getName(), new MyCommand());

        nkConsole("&b插件开启");
    }

    @Override
    public void onDisable(){
        //插件关闭
        nkConsole("&b插件关闭");
    }

}
