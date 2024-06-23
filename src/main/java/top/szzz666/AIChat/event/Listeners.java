package top.szzz666.AIChat.event;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerQuitEvent;

import static top.szzz666.AIChat.AIChatMain.nkServer;
import static top.szzz666.AIChat.AIChatMain.playerChat;
import static top.szzz666.AIChat.config.LangConfig.broadcastMsg;
import static top.szzz666.AIChat.config.LangConfig.requestExcessiveMsg;
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
        if (isBroadcast <= maxRequestNum) {
            isBroadcast++;
            new Thread(() -> BroadcastMessage(msg, player)).start();
        }else {
            new Thread(() -> BroadcastMessage(msg, player)).start();
        }
    }

    private void BroadcastMessage(String msg, Player player) {
        String sendMsg = handleMsg(msg);
        addPlayerChat(player, sendMsg);
        String sr = aiChat(player);
        addMessage("assistant", sr, player);
        nkServer.broadcastMessage(broadcastMsg.replaceAll("%msg%", sr));
        isBroadcast--;
    }
}
