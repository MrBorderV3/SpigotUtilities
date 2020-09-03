package me.border.spigotutilities.example;

import me.border.spigotutilities.baseutils.CommandUtils;
import me.border.spigotutilities.baseutils.ChatUtils;
import me.border.spigotutilities.command.ICommand;
import org.bukkit.command.CommandSender;

public class ExampleCommand extends ICommand {
    protected ExampleCommand() {
        super("example", true, "permission");
    }

    @Override
    public boolean commandUsed(CommandSender sender, String[] args) {
        if (CommandUtils.argsCheck(sender, 0, args)) return true;

        ChatUtils.sendRawMsg(sender, "Example CommandAnno");
        return false;
    }
}
