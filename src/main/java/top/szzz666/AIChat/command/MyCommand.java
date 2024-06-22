package top.szzz666.AIChat.command;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;

import static top.szzz666.AIChat.config.LangConfig.OpFormCmd_description;
import static top.szzz666.AIChat.config.MyConfig.OpenFormCmd;
import static top.szzz666.AIChat.form.MyForm.mainForm;


public class MyCommand extends Command {
    public MyCommand() {
        super(OpenFormCmd, OpFormCmd_description);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender.isOp()) {
            Player player = (Player) sender;
            mainForm(player);
        }
        return false;
    }

}
