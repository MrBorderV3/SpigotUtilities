package me.border.spigotutilities.example;

import me.border.spigotutilities.baseutils.CommandUtils;
import me.border.spigotutilities.command.ICommand;
import me.border.spigotutilities.baseutils.ChatUtils;
import org.bukkit.command.CommandSender;

public class ExampleCommand extends ICommand {

    public ExampleCommand() {
        super("example", true, "exmaple.example");
    }

    @Override
    public boolean commandUsed(CommandSender sender, String[] args) {
        if (CommandUtils.argsCheck(sender, 0, args)) return true;

        ChatUtils.sendRawMsg(sender, "Example Command");
        return false;
    }
}
