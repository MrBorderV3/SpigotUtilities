package me.border.spigotutilities.mojang;

import me.border.spigotutilities.mojang.api.MojangCacheManager;
import me.border.spigotutilities.mojang.api.MojangWebManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerInfo {

    /**
     * Get a name of a player using their uuid.
     *
     * This method first searches the player in the server, if they are found it caches their data and returns.
     * The method will then search in the cache, if they are found it will return.
     * Lastly the method will use the mojang api {@link MojangWebManager} to get the name of the player with the given uuid.
     *
     * @param uuid The uuid.
     * @return The name of the player.
     */
    public static String getName(UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        if (player != null){
            MojangCacheManager.updateCache(uuid, player.getName());
            return player.getName();
        }

        try {
            String tryName = MojangCacheManager.getUsername(uuid);
            if (tryName != null) {
                return tryName;
            }
        } catch (NullPointerException ignored){ }

        return MojangWebManager.getUsername(uuid);
    }

    public static UUID getUUID(String username){
        Player player = Bukkit.getPlayerExact(username);
        if (player != null){
            MojangCacheManager.updateCache(player.getUniqueId(), player.getName());
            return player.getUniqueId();
        }

        try {
            UUID tryId = MojangCacheManager.getUUID(username);
            if (tryId != null) {
                return tryId;
            }
        } catch (NullPointerException ignored){ }

        return MojangWebManager.getUUID(username);
    }
}
