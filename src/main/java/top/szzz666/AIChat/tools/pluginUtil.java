package top.szzz666.AIChat.tools;

import cn.nukkit.utils.TextFormat;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import okhttp3.*;
import top.szzz666.AIChat.entity.Message;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static top.szzz666.AIChat.AIChatMain.*;
import static top.szzz666.AIChat.config.LangConfig.requestFailedMsg;
import static top.szzz666.AIChat.config.MyConfig.*;

public class pluginUtil {
    public static String aiChat(String username) {
        String jsonResponse = "";
        String strResponse = "";
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(clientOutTime.get(0), TimeUnit.SECONDS) // 连接超时时间
                .readTimeout(clientOutTime.get(1), TimeUnit.SECONDS)     // 读取超时时间
                .writeTimeout(clientOutTime.get(2), TimeUnit.SECONDS)    // 写入超时时间
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        String requestJson = initRequestJson(username);
        RequestBody body = RequestBody.create(mediaType, requestJson);
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
            jsonResponse = Objects.requireNonNull(response.body()).string();
        } catch (IOException e) {
            e.fillInStackTrace();
        }
        if (getCode(strResponse).equals("200")) {
            return getContent(jsonResponse);
        } else {
            nkConsole("出现错误: \n" + requestJson + "\n" + strResponse);
            playerChat.remove(username);
            ArrayList<Message> messages = new ArrayList<>();
            playerChat.put(username, messages);
            addMessage("system", getPrompt(), username);
            return requestFailedMsg;
        }
    }

    public static String initRequestJson(String username) {
//        JsonObject jsonObject = new JsonParser().parse(readFileToString(ConfigPath + "/requestJson.json")).getAsJsonObject();
        JsonObject jsonObject;
        try {
            FileReader fileReader = new FileReader(ConfigPath + "/requestJson.json");
            JsonReader reader = new JsonReader(fileReader);
            reader.setLenient(true); // 设置宽松模式
            jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        // 修改字段
        jsonObject.add("messages", JsonParser.parseString(new Gson().toJson(playerChat.get(username))));
        jsonObject.add("user", JsonParser.parseString(username));
        return jsonObject.toString();
    }

    public static String getPrompt() {
        return readFileToString(ConfigPath + "/" + prompt);
    }

    //添加消息
    public static void addMessage(String role, String content, String username) {
        Message message = new Message();
        message.setRole(role);
        message.setContent(content);
        playerChat.get(username).add(message);
    }

    //添加玩家聊天
    public static void addPlayerChat(String username, String msg) {
        if (playerChat.get(username) != null) {
            addMessage("user", msg, username);
        } else {
            ArrayList<Message> messages = new ArrayList<>();
            playerChat.put(username, messages);
            addMessage("system", getPrompt(), username);
            addMessage("user", msg, username);
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

    public static String handleMsg(String msg, boolean deprefix) {
        if (deprefix) {
            return handleMsg(msg);
        }
        return msg;
    }


    //前缀判断
//    public static boolean startsWiths(String str, ArrayList<String> prefixs) {
//        for (String prefix : prefixs) {
//            if (str.startsWith(prefix)) {
//                return true;
//            }
//        }
//        return false;
//    }

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
            nkConsole("解析json出错" + strJson, 2);
            return requestFailedMsg;
        }
    }

    //读取文件
    public static String readFileToString(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
//                content.append(System.lineSeparator());
            }
        } catch (IOException e) {
            nkConsole("错误：" + e, 2);
        }
        return content.toString();
    }


    public static Integer[] StringArrToIntArr(String[] stringArray) {
        List<Integer> intList = new ArrayList<>();

        for (String str : stringArray) {
            try {
                Integer number = Integer.parseInt(str);
                intList.add(number);
            } catch (NumberFormatException e) {
                // 忽略无法转换的元素
            }
        }

        // 将ArrayList转换为整数数组
        Integer[] intArray = new Integer[intList.size()];
        for (int i = 0; i < intList.size(); i++) {
            intArray[i] = intList.get(i);
        }

        return intArray;
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

    //将输入的字符串按行打印到控制台。
    public static void lineConsole(String s) {
        String[] lines = s.split("\n");
        for (String line : lines) {
            nkConsole(line);
        }
    }

    public static boolean isBtr(String text) {
        for (String str : blurTriggerWords) {
            if (text != null && text.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTri(String text) {
        for (String str : triggerPrefix) {
            if (text != null && text.startsWith(str)) {
                return true;
            }
        }
        return false;
    }

    // 重置
    public static String delSpace(String str) {
        if (str == null){
            return "";
        }
        return str.replace(" ", "");
    }

}
