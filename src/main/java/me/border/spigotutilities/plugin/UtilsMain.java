package me.border.spigotutilities.plugin;

import me.border.spigotutilities.config.ConfigCache;
import org.bukkit.plugin.java.JavaPlugin;

public class UtilsMain {

    private static JavaPlugin plugin;

    private static boolean usingSpigotPlugin = false;
    private static boolean usingConfigCache = true;

    public static JavaPlugin getInstance(){
        return plugin;
    }

    /**
     * Init SpigotUtilities with the given plugin.
     *
     * @param plugin The plugin
     */
    public static void init(JavaPlugin plugin){
        UtilsMain.plugin = plugin;
        plugin.saveDefaultConfig();
        plugin.getConfig().options().copyDefaults(true);
    }

    static void setUsingSpigotPlugin(boolean usingSpigotPlugin){
        UtilsMain.usingSpigotPlugin = usingSpigotPlugin;
    }

    /**
     * Get whether {@link SpigotPlugin} is being used or not
     *
     * @return {@code true} if its used {@code false} if it isn't.
     */
    public static boolean isUsingSpigotPlugin() {
        return usingSpigotPlugin;
    }

    public static void setConfigCache(boolean usingConfigCache) {
        UtilsMain.usingConfigCache = usingConfigCache;
    }

    /**
     * Get whether {@link ConfigCache} is being used or not
     *
     * @return {@code true} if its used {@code false} if it isn't.
     */
    public static boolean isUsingConfigCache() {
        return usingConfigCache;
    }
}
