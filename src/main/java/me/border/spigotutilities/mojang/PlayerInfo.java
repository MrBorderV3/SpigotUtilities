package me.border.spigotutilities.mojang;

import me.border.spigotutilities.mojang.api.MojangCacheManager;
import me.border.spigotutilities.mojang.api.MojangWebManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerInfo {

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
        } catch (NullPointerException ignored){
        }

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
        } catch (NullPointerException ignored){
        }

        return MojangWebManager.getUUID(username);
    }
}
