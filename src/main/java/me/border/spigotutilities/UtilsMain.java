package me.border.spigotutilities;

import me.border.spigotutilities.mojang.listener.PlayerJoinHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class UtilsMain {

    private static JavaPlugin plugin;

    public static JavaPlugin getInstance(){
        return plugin;
    }

    public static void init(JavaPlugin plugin){
        UtilsMain.plugin = plugin;
        Utils.registerListener(new PlayerJoinHandler());
    }
}
