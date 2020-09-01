package me.border.spigotutilities;

import me.border.spigotutilities.UtilsMain;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static me.border.spigotutilities.player.ChatUtils.*;

public class Utils {
    private static JavaPlugin plugin = UtilsMain.getInstance();

    public static String cs(String s) {
        return plugin.getConfig().getString(s);
    }

    public static String ucs(String s) {
        return colorize(plugin.getConfig().getString(s));
    }

    public static List<String> csl(String s) {
        try {
            return plugin.getConfig().getStringList(s);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public static List<String> csl(String path, String replace, String replacement) {
        List<String> oldList = csl(path);
        if (oldList == null) return null;
        List<String> newList = new ArrayList<>();
        for (String line : oldList) {
            newList.add(line.replaceAll(replace, replacement));
        }
        return newList;
    }

    public static int ci(String s) {
        return plugin.getConfig().getInt(s);
    }

    public static double cd(String s) {
        return plugin.getConfig().getDouble(s);
    }

    public static short cis(String path) {
        return (short) plugin.getConfig().getInt(path);
    }

    public static boolean cb(String s) {
        return plugin.getConfig().getBoolean(s);
    }

    public static void set(String path, Object value){
        plugin.getConfig().set(path, value);
    }

    public static void saveConfig(){
        plugin.saveConfig();
    }

    public static List<String> colorList(List<String> ogList) {
        List<String> newList = new ArrayList<>();
        for (String listLine : ogList) {
            newList.add(colorize(listLine));
        }
        return newList;
    }

    public static List<String> colorList(String path) {
        List<String> newList = new ArrayList<>();
        for (String listLine : csl(path)) {
            newList.add(colorize(listLine));
        }
        return newList;
    }

    public static JavaPlugin initializePlugin(){
        plugin.saveDefaultConfig();
        plugin.getConfig().options().copyDefaults(true);
        return plugin;
    }

    public static void registerCommand(CommandExecutor commandExecutor, String command) {
        plugin.getCommand(command).setExecutor(commandExecutor);
    }

    public static void registerListener(Listener listener){
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}
