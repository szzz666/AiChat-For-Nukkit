package top.szzz666.AIChat.form;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.handler.FormResponseHandler;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.form.window.FormWindowSimple;
import top.szzz666.AIChat.config.MyConfig;

import java.util.ArrayList;
import java.util.Arrays;

import static top.szzz666.AIChat.config.LangConfig.*;
import static top.szzz666.AIChat.config.MyConfig.*;


public class MyForm {
    public static void mainForm(Player player) {
        FormWindowSimple form = new FormWindowSimple(mainForm_title, mainForm_content);
        form.addButton(new ElementButton(mainForm_button1));
        form.addButton(new ElementButton(mainForm_button2));
        form.addHandler(FormResponseHandler.withoutPlayer(ignored -> {
            if (form.wasClosed()) return;
            int buttonIndex = form.getResponse().getClickedButtonId();
            if (buttonIndex == 0) {
                Form0(player);
            } else {
                loadConfig();
                loadLangConfig();
                player.sendMessage(mainForm_sendMessage);
            }
        }));
        player.showFormWindow(form);
    }

    public static void Form0(Player player) {
        FormWindowCustom form = new FormWindowCustom(Form0_title);
        // 添加组件
        form.addElement(new ElementInput(Form0_Element0, Language, Language));
        form.addElement(new ElementInput(Form0_Element1, api_url, api_url));
        form.addElement(new ElementInput(Form0_Element2, api_key, api_key));
        form.addElement(new ElementInput(Form0_Element3, String.valueOf(maxRequestNum), String.valueOf(maxRequestNum)));
        form.addElement(new ElementInput(Form0_Element4, prompt, prompt));
        form.addElement(new ElementInput(Form0_Element5,
                triggerPrefix.toString().replaceAll("\\[", "").replace("]", ""),
                triggerPrefix.toString().replaceAll("\\[", "").replace("]", "")));
        // 设置提交操作
        form.addHandler(FormResponseHandler.withoutPlayer(ignored -> {
            if (form.wasClosed()) return;
            String Language = form.getResponse().getInputResponse(0);
            String api_url = form.getResponse().getInputResponse(1);
            String api_key = form.getResponse().getInputResponse(2);
            int maxRequestNum = Integer.parseInt(form.getResponse().getInputResponse(3));
            String prompt = form.getResponse().getInputResponse(4);
            ArrayList<String> triggerPrefix = new ArrayList<>(Arrays.asList(form.getResponse().getInputResponse(5).split(", ")));
            // 处理用户提交的数据
            MyConfig.Language = Language;
            MyConfig.api_url = api_url;
            MyConfig.api_key = api_key;
            MyConfig.maxRequestNum = maxRequestNum;
            MyConfig.prompt = prompt;
            MyConfig.triggerPrefix = triggerPrefix;
            saveConfig();
            loadLangConfig();
            player.sendMessage(Form0_sendMessage);
        }));
        // 显示表单给玩家
        player.showFormWindow(form);
    }
}
