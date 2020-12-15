package me.border.spigotutilities;

import me.border.spigotutilities.inventory.ItemGlow;
import me.border.spigotutilities.mojang.listener.PlayerJoinHandler;
import me.border.spigotutilities.baseutils.Utils;
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

    public static Enchantment registerGlowEnchantment(){
        Enchantment glow = Enchantment.getByKey(new NamespacedKey(plugin, "Glow"));
        if (glow != null) {
            if (glow.getKey().getKey().equals("Glow")) {
                return glow;
            }
        }

        Field f;
        boolean forced = false;
        if (!Enchantment.isAcceptingRegistrations()) {
            try {
                f = Enchantment.class.getDeclaredField("acceptingNew");
                f.setAccessible(true);
                f.set(null, true);
                forced = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            glow = new ItemGlow(new NamespacedKey(plugin, "Glow"));
            Enchantment.registerEnchantment(glow);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (forced) {
            try {
                f = Enchantment.class.getDeclaredField("acceptingNew");
                f.set(null, false);
                f.setAccessible(false);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return glow;
    }
}
