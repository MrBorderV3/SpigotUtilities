package me.border.spigotutilities.baseutils;

import me.border.spigotutilities.config.ConfigCache;
import me.border.spigotutilities.plugin.UtilsMain;
import me.border.spigotutilities.command.ICommand;
import me.border.spigotutilities.plugin.SpigotPlugin;
import me.border.spigotutilities.task.SpigotTask;
import me.border.spigotutilities.task.SpigotTaskBuilder;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static me.border.spigotutilities.baseutils.ChatUtils.colorize;

public class Utils {
    private static final JavaPlugin plugin = UtilsMain.getInstance();

    public static String cs(String path) {
        if (UtilsMain.isUsingConfigCache())
            return ConfigCache.getInstance().getString(path);

        return plugin.getConfig().getString(path);
    }

    public static String ucs(String path) {
        return colorize(cs(path));
    }

    public static List<String> csl(String path) {
        if (UtilsMain.isUsingConfigCache())
            return ConfigCache.getInstance().getStringList(path);

        return plugin.getConfig().getStringList(path);
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

    public static int ci(String path) {
        if (UtilsMain.isUsingConfigCache())
            return ConfigCache.getInstance().getInt(path);

        return plugin.getConfig().getInt(path);
    }

    public static double cd(String path) {
        if (UtilsMain.isUsingConfigCache())
            return ConfigCache.getInstance().getDouble(path);

        return plugin.getConfig().getDouble(path);
    }

    public static long cl(String path){
        if (UtilsMain.isUsingConfigCache())
            return ConfigCache.getInstance().getLong(path);

        return plugin.getConfig().getLong(path);
    }

    public static boolean cb(String path) {
        if (UtilsMain.isUsingConfigCache())
            return ConfigCache.getInstance().getBoolean(path);

        return plugin.getConfig().getBoolean(path);
    }

    public static Object get(String path){
        if (UtilsMain.isUsingConfigCache())
            return ConfigCache.getInstance().get(path);

        return plugin.getConfig().get(path);
    }

    public static void set(String path, Object value){
        plugin.getConfig().set(path, value);
    }

    public static void reloadConfig(){
        if (UtilsMain.isUsingConfigCache())
            ConfigCache.getInstance().clear();

        plugin.reloadConfig();
    }

    public static void saveConfig(){
        plugin.saveConfig();
    }

    public static List<String> colorList(String path) {
        List<String> oldList = csl(path);
        if (oldList == null) return null;
        List<String> newList = new ArrayList<>();
        for (String line : oldList) {
            newList.add(colorize(line));
        }
        return newList;
    }

    /**
     * Util method to run a {@link SpigotTask#runTaskTimer(Plugin, long, long)} repeating task
     *
     * @param task Task to run
     * @param after After how much ticks it should run
     * @param every Every how much ticks it should run
     *
     * @return The built task
     */
    public static SpigotTask runRepeating(SpigotTask task, long after, long every){
        return SpigotTaskBuilder.builder()
                .after(after)
                .every(every)
                .task(task)
                .build();
    }

    /**
     * Util method to build and run a {@link SpigotTask#runTaskLater(Plugin, long)} later task
     *
     * @param task Task to run
     * @param after After how much ticks it should run
     *
     * @return The built task
     */
    public static SpigotTask runLater(SpigotTask task, long after) {
        return SpigotTaskBuilder.builder()
                .after(after)
                .task(task)
                .build();
    }

    /**
     * Util method to register a {@link CommandExecutor} command
     *
     * @param commandExecutor The command executor
     * @param command The command name
     */
    public static void registerCommand(CommandExecutor commandExecutor, String command) {
        if (UtilsMain.isUsingSpigotPlugin()){
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
        if (UtilsMain.isUsingSpigotPlugin()){
            SpigotPlugin.getInstance().registerCommand(command);
        }
    }

    /**
     * Util method to register a {@link Listener}
     *
     * @param listener The listener to register
     */
    public static void registerListener(Listener listener){
        if (UtilsMain.isUsingSpigotPlugin()){
            SpigotPlugin.getInstance().registerListener(listener);
        } else {
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
}
