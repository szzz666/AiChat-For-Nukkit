package top.szzz666.AIChat.entity;

import com.google.gson.Gson;
import lombok.Data;
import lombok.Getter;

@Data
public class Message {
    public String role;
    public String content;
    public String toString() {
        return new Gson().toJson(this);
    }
}
