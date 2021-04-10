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
     * @return The location of the ground or the given location if non found.
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

    /**
     * Get the highest safe two air block space in a location from the given y
     *
     * @param loc The location to search
     * @param y the Y coordinate to look under
     * @return The y coordinate of the block. Returns -1 if non found.
     * @deprecated Unstable and often incorrect. Use {@link #getGroundLocationAt(Location)}
     */
    public static int getHighestSafeBlock(Location loc, int y) {
        boolean foundAir = false;
        for (int i = y; i >= 0; i--) {
            Location tempLoc = new Location(loc.getWorld(), loc.getX(), i, loc.getZ());
            if (tempLoc.getBlock().getType() == Material.AIR) {
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

    /**
     * Get the highest safe two air block space in a location from top to bottom
     *
     * @param loc The location to search
     * @return The y coordinate of the block. Returns -1 if non found.
     * @deprecated Unstable and often incorrect. Use {@link #getGroundLocationAt(Location)}
     */
    public static int getHighestSafeBlock(Location loc) {
        return getHighestSafeBlock(loc, 256);
    }

    /**
     * Get the highest safe two air block space under a player.
     *
     * @param player The player to search against
     * @return The y coordinate of the block. Returns -1 if non found.
     * @deprecated Unstable and often incorrect. Use {@link #getGroundLocationAt(Location)}
     */
    public static int getHighestSafeBlock(Player player){
        Location loc = player.getLocation();
        return getHighestSafeBlock(loc, (int) loc.getY()+1);
    }
}
