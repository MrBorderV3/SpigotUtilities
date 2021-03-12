package me.border.spigotutilities.mojang;

import me.border.spigotutilities.mojang.api.MojangCacheManager;
import me.border.spigotutilities.mojang.api.MojangWebManager;
import me.border.utilities.cache.CacheMap;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * This class is used to replace bukkit's {@link OfflinePlayer#getName()} and {@link OfflinePlayer#getUniqueId()}.
 * This class use {@link CacheMap} and other functions to reduce the amount of calls being made to the Mojang API.
 * If it must use the Mojang API it will update the received data in the {@link CacheMap} beforehand for easier access if the player is queued again.
 */
public class PlayerInfo {

    /**
     * Get a name of a player using their uuid.
     *
     * This method first searches the player in the server, if they are found it caches their data and returns.
     * The method will then search in the cache, if they are found it will return.
     * Lastly the method will use the Mojang API {@link MojangWebManager#getUsername(UUID)} to get the name of the player with the given uuid.
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


    /**
     * Get a {@link UUID} of a player using their name.
     *
     * This method first searches the player in the server, if they are found it caches their data and returns.
     * The method will then search in the cache, if they are found it will return.
     * Lastly the method will use the Mojang API {@link MojangWebManager#getUUID(String)} to get the UUID of the player with the given name.
     *
     * @param username The name.
     * @return The {@link UUID} of the player.
     */
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
