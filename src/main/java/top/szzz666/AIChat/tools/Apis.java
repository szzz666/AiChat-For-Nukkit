package top.szzz666.AIChat.tools;

import top.szzz666.AIChat.entity.Message;

import java.util.ArrayList;

import static top.szzz666.AIChat.AIChatMain.playerChat;
import static top.szzz666.AIChat.config.LangConfig.TooMuchContextMsg;
import static top.szzz666.AIChat.config.LangConfig.resetMsg;
import static top.szzz666.AIChat.config.MyConfig.maxContextNum;
import static top.szzz666.AIChat.config.MyConfig.resetCommands;
import static top.szzz666.AIChat.tools.pluginUtil.*;

public class Apis {
    public static String getAIResponse(String username, String text) {
        if (playerChat.get(username) == null) {
            ArrayList<Message> sendAiMessages = new ArrayList<>();
            playerChat.put(username, sendAiMessages);
            addMessage("system", getPrompt(), username);
            addMessage("user", handleMsg(text, false), username);
        } else {
            addMessage("user", handleMsg(text, false), username);
        }
        if (resetCommands.contains(delSpace(text))) {
            playerChat.remove(username);
            return resetMsg;
        }
        if (playerChat.get(username).size() > maxContextNum) {
            playerChat.remove(username);
            return TooMuchContextMsg;
        }
        String sr = aiChat(username);
        addMessage("assistant", sr, username);
        return sr;
    }
}
