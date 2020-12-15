package me.border.spigotutilities.baseutils;

import me.border.spigotutilities.UtilsMain;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import static me.border.spigotutilities.baseutils.Utils.*;

public class CommandUtils {

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
