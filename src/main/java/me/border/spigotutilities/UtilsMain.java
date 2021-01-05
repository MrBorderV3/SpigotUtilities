package me.border.spigotutilities;

import me.border.spigotutilities.inventory.ItemGlow;
import me.border.spigotutilities.mojang.listener.PlayerJoinHandler;
import me.border.spigotutilities.baseutils.Utils;
import me.border.utilities.filewatcher.FileWatcher;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class UtilsMain {

    private static JavaPlugin plugin;

    public static JavaPlugin getInstance(){
        return plugin;
    }

    public static void init(JavaPlugin plugin){
        UtilsMain.plugin = plugin;
        plugin.saveDefaultConfig();
        plugin.getConfig().options().copyDefaults(true);
        Utils.registerListener(new PlayerJoinHandler());
    }
}
