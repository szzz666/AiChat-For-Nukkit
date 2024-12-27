package top.szzz666.AIChat.config;
import cn.nukkit.utils.Config;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static top.szzz666.AIChat.AIChatMain.ConfigPath;
import static top.szzz666.AIChat.AIChatMain.plugin;
import static top.szzz666.AIChat.tools.pluginUtil.nkConsole;

public class secData {
    public static HashMap<String, String> secData = new HashMap<>();

    public static void loadSecData() {
        plugin.saveResource("requirements.json");
        plugin.saveResource("secdata.yml");
        plugin.saveResource("lid.176.ftz");
        Config secDataConfig = new Config(ConfigPath + "/secdata.yml", Config.YAML);
        Map<String, Object> tempData = secDataConfig.getAll();

        for (Map.Entry<String, Object> entry : tempData.entrySet()) {
            if (entry.getValue() instanceof String) {
                secData.put(entry.getKey(), (String) entry.getValue());
            } else {
                // 处理非字符串类型的值，或者抛出异常
                nkConsole("Value for key " + entry.getKey() + " is not a String");
            }
        }
    }

    public static String getRequirementsListJson() {
        ArrayList<String> rList = new ArrayList<>(secData.keySet());
        return new Gson().toJson(rList)
                .replace("\"", "\\\"");
    }
}

