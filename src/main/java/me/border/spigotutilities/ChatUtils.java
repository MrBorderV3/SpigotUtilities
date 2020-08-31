package me.border.spigotutilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

import static me.border.spigotutilities.Utils.*;

public class ChatUtils {

    private static JavaPlugin plugin = UtilsMain.getInstance();

    public static String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    // Send message to a CommandSender
    public static void sendMsg(CommandSender sender, String path) {
        sender.sendMessage(ucs(path));
    }

    // Send message to a CommandSender with a .replaceAll
    public static void sendMsg(CommandSender sender, String path, String replace, String replacement) {
        sender.sendMessage(ucs(path).replaceAll(replace, replacement));
    }

    // Send message to a CommandSender with 2 .replaceAll
    public static void sendMsg(CommandSender sender, String path, String replace, String replacement, String replace2, String replacement2) {
        sender.sendMessage(ucs(path).replaceAll(replace, replacement).replaceAll(replace2, replacement2));
    }

    // Send a raw message to a CommandSender
    public static void sendRawMsg(CommandSender sender, String message) {
        sender.sendMessage(colorize(message));
    }

    // Send message to a player
    public static void sendMsg(Player player, String path) {
        player.sendMessage(ucs(path));
    }

    // Send message to a player with a .replaceAll
    public static void sendMsg(Player player, String path, String replace, String replacement) {
        player.sendMessage(ucs(path).replaceAll(replace, replacement));
    }

    // Send message to a player with 2 .replaceAll
    public static void sendMsg(Player player, String path, String replace, String replacement, String replace2, String replacement2) {
        player.sendMessage(ucs(path).replaceAll(replace, replacement).replaceAll(replace2, replacement2));
    }

    // Send a raw message to a player
    public static void sendRawMsg(Player player, String message) {
        player.sendMessage(colorize(message));
    }

    public static void sendRawMsg(Player player, String message, String replace, String replacement) {
        player.sendMessage(colorize(message.replaceAll(replace, replacement)));
    }

    public static void sendRawMsg(Player player, String message, String replace, String replacement, String replace2, String replacement2) {
        player.sendMessage(colorize(message.replaceAll(replace, replacement).replaceAll(replace2, replacement2)));
    }


    // Convert a string list into messages and send them to a CommandSender
    public static void sendMessageList(CommandSender sender, String path) {
        for (String output : csl(path)) {
            sender.sendMessage(colorize(output));
        }
    }

    // Convert a string list into messages and send them to a CommandSender
    public static void sendMessageListFormatted(CommandSender sender, String path) {
        for (String output : csl(path)) {
            sender.sendMessage(colorize("&6" + output));
        }
    }


    // Convert a raw string list into messages and send them to a CommandSender
    public static void sendRawMessageList(CommandSender sender, List<String> messageList) {
        for (String output : messageList) {
            sender.sendMessage(colorize(output));
        }
    }

    // Convert a string list into messages and send them to a player
    public static void sendMessageList(Player player, String path) {
        for (String output : csl(path)) {
            player.sendMessage(colorize(output));
        }
    }

    // Convert a raw string list into messages and send them to a player
    public static void sendRawMessageList(Player player, List<String> messageList) {
        for (String output : messageList) {
            player.sendMessage(colorize(output));
        }
    }

    public static void broadcastMessage(String path) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendMsg(player, path);
        }
    }

    public static void broadcastMessage(String path, String replace, String replacement) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendMsg(player, path, replace, replacement);
        }
    }

    public static void broadcastMessage(String path, String replace, String replacement, String replace2, String replacement2) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendMsg(player, path, replace, replacement, replace2, replacement2);
        }
    }


    public static void broadcastMessageRaw(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendRawMsg(player, message);
        }
    }
}
