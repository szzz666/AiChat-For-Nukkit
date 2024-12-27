package top.szzz666.AIChat.tools;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import top.szzz666.AIChat.config.secData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static top.szzz666.AIChat.AIChatMain.ConfigPath;
import static top.szzz666.AIChat.AIChatMain.fastText;
import static top.szzz666.AIChat.config.MyConfig.*;
import static top.szzz666.AIChat.config.secData.getRequirementsListJson;
import static top.szzz666.AIChat.tools.pluginUtil.*;

public class secUtil {
    public static String getCmdBySec(String sentence) {
        HashMap<String, Double> scoreMap = new HashMap<>();
        for (String key : secData.secData.keySet()) {
            double score = fastText.semanticMatchingScore(key, sentence);
            nkConsole("&b" + secData.secData.get(key) + " &r: " + score);
            if (score > minScore) {
                scoreMap.put(secData.secData.get(key), score);
            }
        }
        String maxKey = null;
        Double maxValue = Double.MIN_VALUE;
        for (Map.Entry<String, Double> entry : scoreMap.entrySet()) {
            if (entry.getValue() > maxValue) {
                maxValue = entry.getValue();
                maxKey = entry.getKey();
            }
        }
        return maxKey;
    }

    public static String getCmdByAi(String sentence) {
        String requirements = aiRequirements(sentence);
        if (secData.secData.containsKey(requirements)) {
            return secData.secData.get(requirements);
        }
        return null;
    }

    public static String aiRequirements(String msg) {
        String jsonResponse = "";
        String strResponse = "";
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(clientOutTime.get(0), TimeUnit.SECONDS) // 连接超时时间
                .readTimeout(clientOutTime.get(1), TimeUnit.SECONDS)     // 读取超时时间
                .writeTimeout(clientOutTime.get(2), TimeUnit.SECONDS)    // 写入超时时间
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        String requestJson = initRtsRequestJson(msg);
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
        nkConsole("&b" + requestJson + "\n###" + jsonResponse);
        return getContent(jsonResponse);
    }

    public static String initRtsRequestJson(String msg) {
        JsonObject jsonObject = new JsonParser().parse(readFileToString(ConfigPath + "/requirements.json")).getAsJsonObject();
        // 修改字段
        jsonObject.add("user", JsonParser.parseString("user"));
        return jsonObject.toString()
                .replace("%requirementsList%", getRequirementsListJson())
                .replace("%msg%", msg);
    }
}
