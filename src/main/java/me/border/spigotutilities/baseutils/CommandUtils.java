package me.border.spigotutilities.baseutils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.border.spigotutilities.baseutils.ChatUtils.*;

public class CommandUtils {

    // Check if the args are right, returns true/false.
    public static boolean argsCheck(CommandSender sender, int allowed, String[] args) {
        if (args.length != allowed) {
            sendMsg(sender,"illegalArguments");
            return false;
        }
        return true;
    }

    // Check if the target is a player, returns true/false.
    public static boolean playerCheck(CommandSender sender) {
        if (sender instanceof Player) {
            return true;
        }
        sendMsg(sender,"notAPlayer");
        return false;
    }

    // Check if the target is offline/null, returns true/false.
    public static boolean offlineCheck(Player target, CommandSender sender, String replacement) {
        if (target == null) {
            sendMsg(sender,"targetOffline", "%target%", replacement);
            return false;
        }
        return true;
    }

    // Check if the sender has the needed permission, returns true/false.
    public static boolean permCheck(CommandSender sender, String perm) {
        if (sender.hasPermission(perm)) {
            return true;
        }
        sendMsg(sender,"noPermission");
        return false;
    }

    // Check if the sender and the target are the same, returns true/false.
    public static boolean duplicateCheck(Player sender, Player target) {
        if (sender == target) {
            sendMsg(sender,"duplicateMessage");
            return false;
        }
        return true;
    }
}
