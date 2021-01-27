package me.border.spigotutilities;

import org.bukkit.plugin.java.JavaPlugin;

public class UtilsMain {

    private static JavaPlugin plugin;

    public static JavaPlugin getInstance(){
        return plugin;
    }

    public static void init(JavaPlugin plugin){
        init(plugin, true);
    }

    public static void init(JavaPlugin plugin, boolean usePlayerInfo){
        UtilsMain.plugin = plugin;
        plugin.saveDefaultConfig();
        plugin.getConfig().options().copyDefaults(true);
        if (usePlayerInfo)
            PlayerInfoAdapter.init();
    }
}
