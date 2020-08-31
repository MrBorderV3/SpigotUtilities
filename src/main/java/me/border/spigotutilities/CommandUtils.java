package me.border.spigotutilities;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import static me.border.spigotutilities.Utils.*;

public class CommandUtils {

    private static Plugin plugin = UtilsMain.getInstance();

    // Check if the args are right, returns true/false.
    public static boolean argsCheck(CommandSender sender, int allowed, String[] args) {
        if (args.length != allowed) {
            sender.sendMessage(ucs("illegalArguments"));
            return false;
        }
        return true;
    }

    // Check if the target is a player, returns true/false.
    public static boolean playerCheck(CommandSender sender) {
        if (sender instanceof Player) {
            return true;
        }
        sender.sendMessage(ucs("notAPlayer"));
        return false;
    }

    // Check if the target is offline/null, returns true/false.
    public static boolean offlineCheck(Player target, CommandSender sender, String replacement) {
        if (target == null) {
            sender.sendMessage(ucs("targetOffline").replaceAll("%target%", replacement));
            return false;
        }
        return true;
    }

    // Check if the sender has the needed permission, returns true/false.
    public static boolean permCheck(CommandSender sender, String perm) {
        if (sender.hasPermission(perm)) {
            return true;
        }
        sender.sendMessage(ucs("noPermission"));
        return false;
    }

    // Check if the sender and the target are the same, returns true/false.
    public static boolean duplicateCheck(Player sender, Player target) {
        if (sender == target) {
            sender.sendMessage(ucs("duplicateMessage"));
            return false;
        }
        return true;
    }
}
