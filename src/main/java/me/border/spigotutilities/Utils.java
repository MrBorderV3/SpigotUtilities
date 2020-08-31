package me.border.spigotutilities;

import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

import static me.border.spigotutilities.ChatUtils.*;

public class Utils {
    private static Plugin plugin = UtilsMain.getInstance();

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
}
