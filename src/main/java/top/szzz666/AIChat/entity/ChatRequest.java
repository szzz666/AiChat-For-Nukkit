package top.szzz666.AIChat.entity;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ChatRequest {
    @SerializedName("messages")
    private ArrayList<Message> messages;

    @SerializedName("user")
    private String user;

    @SerializedName("model")
    private String model;

    @SerializedName("frequency_penalty")
    private double frequencyPenalty;

    @SerializedName("max_tokens")
    private int maxTokens;

    @SerializedName("presence_penalty")
    private double presencePenalty;

    @SerializedName("stop")
    private String stop;

    @SerializedName("stream")
    private boolean stream;

    @SerializedName("temperature")
    private double temperature;

    @SerializedName("top_p")
    private double topP;

    @SerializedName("logprobs")
    private boolean logprobs;

    @SerializedName("top_logprobs")
    private String topLogprobs;


    public ChatRequest() {
    }

    public ChatRequest(ArrayList<Message> messages, String user, String model, double frequencyPenalty, int maxTokens, double presencePenalty, String stop, boolean stream, double temperature, double topP, boolean logprobs, String topLogprobs) {
        this.messages = messages;
        this.user = user;
        this.model = model;
        this.frequencyPenalty = frequencyPenalty;
        this.maxTokens = maxTokens;
        this.presencePenalty = presencePenalty;
        this.stop = stop;
        this.stream = stream;
        this.temperature = temperature;
        this.topP = topP;
        this.logprobs = logprobs;
        this.topLogprobs = topLogprobs;
    }

    /**
     * 获取
     * @return messages
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     * 设置
     * @param messages
     */
    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    /**
     * 获取
     * @return user
     */
    public String getUser() {
        return user;
    }

    /**
     * 设置
     * @param user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * 获取
     * @return model
     */
    public String getModel() {
        return model;
    }

    /**
     * 设置
     * @param model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 获取
     * @return frequencyPenalty
     */
    public double getFrequencyPenalty() {
        return frequencyPenalty;
    }

    /**
     * 设置
     * @param frequencyPenalty
     */
    public void setFrequencyPenalty(double frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
    }

    /**
     * 获取
     * @return maxTokens
     */
    public int getMaxTokens() {
        return maxTokens;
    }

    /**
     * 设置
     * @param maxTokens
     */
    public void setMaxTokens(int maxTokens) {
        this.maxTokens = maxTokens;
    }

    /**
     * 获取
     * @return presencePenalty
     */
    public double getPresencePenalty() {
        return presencePenalty;
    }

    /**
     * 设置
     * @param presencePenalty
     */
    public void setPresencePenalty(double presencePenalty) {
        this.presencePenalty = presencePenalty;
    }

    /**
     * 获取
     * @return stop
     */
    public String getStop() {
        return stop;
    }

    /**
     * 设置
     * @param stop
     */
    public void setStop(String stop) {
        this.stop = stop;
    }

    /**
     * 获取
     * @return stream
     */
    public boolean isStream() {
        return stream;
    }

    /**
     * 设置
     * @param stream
     */
    public void setStream(boolean stream) {
        this.stream = stream;
    }

    /**
     * 获取
     * @return temperature
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * 设置
     * @param temperature
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    /**
     * 获取
     * @return topP
     */
    public double getTopP() {
        return topP;
    }

    /**
     * 设置
     * @param topP
     */
    public void setTopP(double topP) {
        this.topP = topP;
    }

    /**
     * 获取
     * @return logprobs
     */
    public boolean isLogprobs() {
        return logprobs;
    }

    /**
     * 设置
     * @param logprobs
     */
    public void setLogprobs(boolean logprobs) {
        this.logprobs = logprobs;
    }

    /**
     * 获取
     * @return topLogprobs
     */
    public String getTopLogprobs() {
        return topLogprobs;
    }

    /**
     * 设置
     * @param topLogprobs
     */
    public void setTopLogprobs(String topLogprobs) {
        this.topLogprobs = topLogprobs;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
