package top.szzz666.AIChat.event;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.scheduler.AsyncTask;
import top.szzz666.AIChat.entity.Message;
import top.szzz666.AIChat.tools.secUtil;

import java.util.ArrayList;
import java.util.HashMap;

import static top.szzz666.AIChat.AIChatMain.*;
import static top.szzz666.AIChat.config.LangConfig.*;
import static top.szzz666.AIChat.config.MyConfig.*;
import static top.szzz666.AIChat.tools.pluginUtil.*;


public class Listeners implements Listener {
    int isBroadcast = 0;
    HashMap<Player, String> playerCmd = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(broadcastMsg.replace("%msg%", joinMsg));
    }

    @EventHandler
    public void onPlayerChat(PlayerChatEvent event) {
        String msg = event.getMessage();
        Player player = event.getPlayer();
        if (!triggerPrefix.isEmpty() && !blurTriggerWords.isEmpty()) {
            if (isBtr(msg) || isTri(msg)) {
                ProcessMessages(event, msg, player);
            }
        } else {
            ProcessMessages(event, msg, player);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        playerChat.remove(player.getName());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (playerCmd.containsKey(player)) {
            nkServer.dispatchCommand(player, playerCmd.get(player));
            playerCmd.remove(player);
        }
    }

    private void ProcessMessages(PlayerChatEvent event, String msg, Player player) {
        // 先让消息发送出去
        event.setCancelled(false);
        nkServer.getScheduler().scheduleAsyncTask(plugin, new AsyncTask() {
            @Override
            public void onRun() {
                String cmd = null;
                if (autoCmd) {
                    if (useFastText) {
                        cmd = secUtil.getCmdBySec(msg);
                    } else {
                        cmd = secUtil.getCmdByAi(msg);
                    }
                }
                if (cmd == null) {
                    if (isBroadcast <= maxRequestNum) {
                        isBroadcast++;
                        BroadcastMessage(msg, player);
                    } else {
                        nkServer.broadcastMessage(broadcastMsg.replaceAll("%msg%", requestExcessiveMsg));
                    }
                } else {
                    player.sendMessage(broadcastMsg.replace("%msg%", Sec_sendMessage));
                    playerCmd.put(player, cmd);
                }
            }
        });
    }

    private void BroadcastMessage(String msg, Player player) {
        if (playerChat.get(player.getName()) == null) {
            ArrayList<Message> sendAiMessages = new ArrayList<>();
            playerChat.put(player.getName(), sendAiMessages);
            addMessage("system", getPrompt(), player.getName());
            addMessage("user", handleMsg(msg, false), player.getName());
        } else {
            addMessage("user", handleMsg(msg, false), player.getName());
        }
        if (resetCommands.contains(delSpace(msg))) {
            playerChat.remove(player.getName());
            nkServer.broadcastMessage(broadcastMsg.replace("%msg%", resetMsg));
            return;
        }
        if (playerChat.get(player.getName()).size() > maxContextNum) {
            playerChat.remove(player.getName());
            nkServer.broadcastMessage(broadcastMsg.replace("%msg%", TooMuchContextMsg));
            return;
        }
        String sr = aiChat(player.getName());
        isBroadcast--;
        addMessage("assistant", sr, player.getName());
        nkServer.broadcastMessage(broadcastMsg.replace("%msg%", sr));
        if (sr.equals(requestFailedMsg)) {
            playerChat.remove(player.getName());
        }
    }
}
