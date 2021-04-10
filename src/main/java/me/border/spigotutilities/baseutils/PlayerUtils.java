package me.border.spigotutilities.baseutils;

import com.cryptomorin.xseries.XSound;
import me.border.spigotutilities.plugin.UtilsMain;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BlockIterator;

import static me.border.spigotutilities.baseutils.Utils.*;

public class PlayerUtils {

    private static final JavaPlugin plugin = UtilsMain.getInstance();

    public static void playSound(Player p, Sound sound) {
        p.playSound(p.getLocation(), sound, 2F, 1F);
    }

    public static void playSound(Player p, XSound sound, float volume, float pitch) {
        p.playSound(p.getLocation(), sound.parseSound(), volume, pitch);
    }

    public static void playSound(Player p, String sound) {
        playSound(p, sound, 2F, 1F);
    }

    public static void playSound(Player p, String sound, float volume, float pitch) {
        playSound(p, XSound.valueOf(cs(sound)), volume, pitch);
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

    /**
     * Get the highest safe block in the given location
     *
     * @param location The location to search
     * @return The location of the safe block.
     */
    public static Location getGroundLocationAt(Location location) {
        World world = location.getWorld();
        Block highest = world != null ? world.getHighestBlockAt(location).getRelative(BlockFace.DOWN) : null;
        Block block = highest != null && highest.getY() < location.getY() ? highest : location.getBlock();

        while(!block.getType().isSolid() && block.getLocation().getY() >= 0) {
            block = block.getRelative(BlockFace.DOWN);
        }

        return new Location(location.getWorld(), location.getX(),
                block.getY() >= 0 ? block.getY() + 1 : location.getY(),
                location.getZ(), location.getYaw(), location.getPitch());
    }
}
