package me.border.spigotutilities.baseutils;

import me.border.spigotutilities.UtilsMain;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BlockIterator;

import static me.border.spigotutilities.baseutils.Utils.*;

public class PlayerUtils {

    private static JavaPlugin plugin = UtilsMain.getInstance();

    public static void playSound(Player p, Sound sound) {
        p.playSound(p.getLocation(), sound, 2F, 1F);
    }

    public static void playSound(Player p, Sound sound, float volume, float pitch) {
        p.playSound(p.getLocation(), sound, volume, pitch);
    }

    public static void playSound(Player p, String sound) {
        p.playSound(p.getLocation(), Sound.valueOf(cs(sound)), 2F, 1F);
    }

    public static void playSound(Player p, String sound, float volume, float pitch) {
        p.playSound(p.getLocation(), Sound.valueOf(cs(sound)), volume, pitch);
    }

    public static void setMetadata(Player p, String key, Object value) {
        p.setMetadata(key, new FixedMetadataValue(plugin, value));
    }

    public static void removeMetadata(Player p, String key) {
        p.removeMetadata(key, plugin);
    }

    public static Block getTargetBlock(Player player, int range) {
        BlockIterator iter = new BlockIterator(player, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }

        return lastBlock;
    }
}
