package me.border.spigotutilities.baseutils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static me.border.spigotutilities.baseutils.Utils.*;

/**
 * Chat related utilities.
 */
public class ChatUtils {

    public static String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    // Send message to a CommandSender
    public static void sendMsg(CommandSender sender, String path) {
        sendRawMsg(sender, cs(path));
    }

    // Send message to a CommandSender with a .replaceAll
    public static void sendMsg(CommandSender sender, String path, String replace, String replacement) {
        sendRawMsg(sender, cs(path), replace, replacement);
    }

    // Send message to a CommandSender with 2 .replaceAll
    public static void sendMsg(CommandSender sender, String path, String replace, String replacement, String replace2, String replacement2) {
        sendRawMsg(sender, cs(path), replace, replacement, replace2, replacement2);
    }

    public static void sendMsg(CommandSender sender, String path, String... patterns){
        String msg = cs(path);
        sendRawMsg(sender, msg, patterns);
    }

    // Send a raw message to a CommandSender
    public static void sendRawMsg(CommandSender sender, String message) {
        sender.sendMessage(colorize(message));
    }

    public static void sendRawMsg(CommandSender sender, String message, String replace, String replacement) {
        sender.sendMessage(colorize(message.replaceAll(replace, replacement)));
    }

    public static void sendRawMsg(CommandSender sender, String message, String replace, String replacement, String replace2, String replacement2) {
        sender.sendMessage(colorize(message.replaceAll(replace, replacement).replaceAll(replace2, replacement2)));
    }

    public static void sendRawMsg(CommandSender sender, String message, String... patterns){
        boolean isRegex = true;
        String tempRegex = "";
        for (String str : patterns){
            if (isRegex) {
                tempRegex = str;
                isRegex = false;
            } else {
                message = message.replaceAll(tempRegex, str);
                isRegex = true;
            }
        }

        sendRawMsg(sender, message);
    }

    // Convert a string list into messages and send them to a CommandSender
    public static void sendMessageList(CommandSender sender, String path) {
        for (String output : csl(path)) {
            sendRawMsg(sender, output);
        }
    }

    // Convert a string list into messages and send them to a CommandSender
    public static void sendMessageListFormatted(CommandSender sender, String path) {
        for (String output : csl(path)) {
            sendRawMsg(sender, "&6" + output);
        }
    }


    // Convert a raw string list into messages and send them to a CommandSender
    public static void sendRawMessageList(CommandSender sender, List<String> messageList) {
        for (String output : messageList) {
            sendRawMsg(sender, output);
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

    public static List<String> colorList(List<String> ogList) {
        List<String> newList = new ArrayList<>();
        for (String listLine : ogList) {
            newList.add(colorize(listLine));
        }
        return newList;
    }
}
