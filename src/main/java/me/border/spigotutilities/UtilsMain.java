package me.border.spigotutilities;

import org.bukkit.plugin.Plugin;

public class UtilsMain {

    private static Plugin plugin;

    public static Plugin getInstance(){
        return plugin;
    }

    public static void init(Plugin plugin){
        UtilsMain.plugin = plugin;
    }
}
