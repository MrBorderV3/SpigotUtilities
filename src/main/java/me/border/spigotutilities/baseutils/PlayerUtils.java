package me.border.spigotutilities.baseutils;

import me.border.spigotutilities.UtilsMain;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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

    /**
     * Get the highest safe two air block space in a location
     *
     * @param loc The location to search
     * @return The y coordinate of the block. Returns -1 if non found.
     */
    public static int getHighestSafeBlock(Location loc){
        boolean foundAir = false;
        for (int i = 128; i >= 0; i--){
            Location tempLoc = new Location(loc.getWorld(), loc.getX(), i, loc.getZ());
            if (tempLoc.getBlock().getType() == Material.AIR){
                if (foundAir) {
                    Block under = tempLoc.getBlock().getRelative(BlockFace.DOWN);
                    if (under.getType().isSolid() && under.getType() != Material.LAVA && under.getType().isBlock()) {
                        return i;
                    }
                } else {
                    foundAir = true;
                }
            } else {
                foundAir = false;
            }
        }

        return -1;
    }
}
