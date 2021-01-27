package me.border.spigotutilities.baseutils;

import me.border.spigotutilities.UtilsMain;
import me.border.spigotutilities.command.ICommand;
import me.border.spigotutilities.plugin.SpigotPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static me.border.spigotutilities.baseutils.ChatUtils.*;

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

    /**
     * Util method to run a {@link org.bukkit.scheduler.BukkitScheduler#runTaskTimer(Plugin, Runnable, long, long)} repeating task
     *
     * @param runnable Task to run
     * @param after After how much ticks it should run
     * @param every Every how much ticks it should run
     */
    public static void runRepeating(Runnable runnable, long after, long every){
        Bukkit.getScheduler().runTaskTimer(plugin, runnable, after, every);
    }

    /**
     * Util method to run a {@link org.bukkit.scheduler.BukkitScheduler#runTaskLater(Plugin, Runnable, long)} later task
     *
     * @param runnable Task to run
     * @param after After how much ticks it should run
     */
    public static void runLater(Runnable runnable, long after) {
        Bukkit.getScheduler().runTaskLater(plugin, runnable, after);
    }

    /**
     * Util method to register a {@link CommandExecutor} command
     *
     * @param commandExecutor The command executor
     * @param command The command name
     */
    public static void registerCommand(CommandExecutor commandExecutor, String command) {
        if (SpigotPlugin.isUsed()){
            SpigotPlugin.getInstance().registerCommand(commandExecutor, command);
        } else {
            plugin.getCommand(command).setExecutor(commandExecutor);
        }
    }

    /**
     * This should only be used if {@link SpigotPlugin} is being used.
     *
     * @see SpigotPlugin#registerCommand(ICommand)
     */
    public static void registerCommand(ICommand command){
        if (SpigotPlugin.isUsed()) {
            SpigotPlugin.getInstance().registerCommand(command);
        }
    }

    /**
     * Util method to register a {@link Listener}
     *
     * @param listener The listener to register
     */
    public static void registerListener(Listener listener){
        if (SpigotPlugin.isUsed()){
            SpigotPlugin.getInstance().registerListener(listener);
        } else {
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
}
