package top.szzz666.AIChat.tools;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import com.google.gson.*;
import okhttp3.*;
import top.szzz666.AIChat.entity.Message;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static top.szzz666.AIChat.AIChatMain.*;
import static top.szzz666.AIChat.config.LangConfig.requestFailedMsg;
import static top.szzz666.AIChat.config.MyConfig.*;

public class pluginUtil {
    public static String aiChat(Player player) {
        String jsonResponse = "";
        String strResponse = "";
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String messagesJson = gson.toJson(playerChat.get(player));
//        nkConsole(messagesJson);
        RequestBody body = RequestBody.create(mediaType, (readJsonFile(ConfigPath + "/requestJson.json")
                .replaceAll("\\[\"messages\"]", messagesJson)));
        Request request = new Request.Builder()
                .url(api_url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + api_key)
                .build();
        try {
            Response response = client.newCall(request).execute();
            //获取响应的json字符串
            strResponse = String.valueOf(response);
            jsonResponse = response.body().string();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
        if (getCode(strResponse).equals("200")) {
            return getContent(jsonResponse);
        } else {
            nkConsole("错误码：" + getCode(strResponse));
            playerChat.remove(player);
            ArrayList<Message> messages = new ArrayList<>();
            playerChat.put(player, messages);
            addMessage("system", getPrompt(), player);
            return requestFailedMsg;
        }
    }
    public static String getPrompt() {
       return readJsonFile(ConfigPath + "/" + prompt);
    }

    //添加消息
    public static void addMessage(String role, String content, Player player) {
        Message message = new Message();
        message.setRole(role);
        message.setContent(content);
        playerChat.get(player).add(message);
    }

    //添加玩家聊天
    public static void addPlayerChat(Player player, String msg) {
        if (playerChat.get(player) != null) {
            addMessage("user", msg, player);
        } else {
            ArrayList<Message> messages = new ArrayList<>();
            playerChat.put(player, messages);
            addMessage("system", getPrompt(), player);
            addMessage("user", msg, player);
        }
    }

    //处理错误code
    public static String getCode(String strResponse) {
        // 定义正则表达式模式
        Pattern pattern = Pattern.compile("code=(\\d+)");
        Matcher matcher = pattern.matcher(strResponse);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "403";
        }
    }

    //处理msg
    public static String handleMsg(String msg) {
        String msg1 = msg;
        for (String prefix : triggerPrefix) {
            msg1 = msg1.substring(prefix.length());
            return msg1;
        }
        return msg1;
    }


    //前缀判断
    public static boolean startsWiths(String str, ArrayList<String> prefixs) {
        for (String prefix : prefixs) {
            if (str.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    //解析json获得content
    public static String getContent(String strJson) {
//        JsonObject messageObject = null;
        try {
            JsonElement jsonElement = JsonParser.parseString(strJson);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray choicesArray = jsonObject.getAsJsonArray("choices");
            JsonObject firstChoice = choicesArray.get(0).getAsJsonObject();
            JsonObject messageObject = firstChoice.getAsJsonObject("message");
            return messageObject.get("content").getAsString();
        } catch (Exception e) {
            nkConsole("解析json出错" + strJson,2);
            return requestFailedMsg;
        }
    }

    //读取json文件
    public static String readJsonFile(String filePath) {
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonContent.toString();
    }

    //使用nk插件的控制台输出
    public static void nkConsole(String msg) {
        plugin.getLogger().info(TextFormat.colorize('&', msg));
    }

    public static void nkConsole(String msg, int typeNum) {
        if (typeNum == 1) {
            plugin.getLogger().warning(TextFormat.colorize('&', msg));
        } else if (typeNum == 2) {
            plugin.getLogger().error(TextFormat.colorize('&', msg));
        } else {
            plugin.getLogger().info(TextFormat.colorize('&', msg));
        }
    }
}
