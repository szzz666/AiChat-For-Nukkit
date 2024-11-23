package top.szzz666.AIChat.event;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.scheduler.AsyncTask;
import com.google.gson.Gson;
import top.szzz666.AIChat.entity.Message;

import java.util.ArrayList;

import static top.szzz666.AIChat.AIChatMain.*;
import static top.szzz666.AIChat.config.LangConfig.*;
import static top.szzz666.AIChat.config.MyConfig.maxRequestNum;
import static top.szzz666.AIChat.config.MyConfig.triggerPrefix;
import static top.szzz666.AIChat.tools.pluginUtil.*;


public class Listeners implements Listener {
    int isBroadcast = 0;

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        String msg = event.getMessage();
        Player player = event.getPlayer();
        if (!triggerPrefix.isEmpty()) {
            if (startsWiths(msg, triggerPrefix)) {
                ProcessMessages(event, msg, player);
            }
        } else {
            ProcessMessages(event, msg, player);
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        playerChat.remove(player);
    }

    private void ProcessMessages(PlayerChatEvent event, String msg, Player player) {
        // 先让消息发送出去
        event.setCancelled(false);
        nkServer.getScheduler().scheduleAsyncTask(plugin, new AsyncTask() {
            @Override
            public void onRun() {
                if (isBroadcast <= maxRequestNum) {
                    isBroadcast++;
                    BroadcastMessage(msg, player);
                }else {
                    nkServer.broadcastMessage(broadcastMsg.replaceAll("%msg%", requestExcessiveMsg));
                }
            }
        });
//        new Thread(() -> {/*d代码*/}).start();
//        if (isBroadcast <= maxRequestNum) {
//            isBroadcast++;
//            new Thread(() -> BroadcastMessage(msg, player)).start();
//        }else {
//            new Thread(() -> nkServer.broadcastMessage(broadcastMsg.replaceAll("%msg%", requestExcessiveMsg))).start();
//        }
    }

    private void BroadcastMessage(String msg, Player player) {
//        String sendMsg = handleMsg(msg);
//        addPlayerChat(player, sendMsg);
//        String sr = aiChat(player);
//        addMessage("assistant", sr, player);
//        nkServer.broadcastMessage(broadcastMsg.replaceAll("%msg%", sr));
        if (playerChat.get(player) == null) {
            ArrayList<Message> sendAiMessages = new ArrayList<>();
            playerChat.put(player, sendAiMessages);
            addMessage("system", getPrompt(), player);
            addMessage("user", handleMsg(msg), player);
        } else {
            addMessage("user", handleMsg(msg), player);
        }
        String sr = aiChat(player);
        isBroadcast--;
        addMessage("assistant", sr, player);
        nkServer.broadcastMessage(broadcastMsg.replaceAll("%msg%", sr));
        if (sr.equals(requestFailedMsg)){
            playerChat.remove(player);
        }
//        isBroadcast--;
    }
}
