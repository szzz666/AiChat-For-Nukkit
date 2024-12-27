package top.szzz666.AIChat.entity;

import com.google.gson.Gson;

public class Message {
    public String role;
    public String content;

    public Message() {
    }

    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }

    /**
     * 获取
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * 设置
     * @param role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * 获取
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
