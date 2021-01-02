package me.border.spigotutilities.mojang.api;

import me.border.spigotutilities.mojang.utils.Settings;
import me.border.utilities.cache.Cache;
import me.border.utilities.cache.CachedObject;
import me.border.utilities.cache.ExpiringCache;

import java.util.UUID;

public class MojangCacheManager {

    private static final Cache<String> uuidCache = new ExpiringCache<>();
    private static final Cache<UUID> nameCache = new ExpiringCache<>();

    public static void updateCache(UUID uuid, String name){
        uuidCache.cache(name, new CachedObject(uuid, Settings.UUID_EXPIRE));
        nameCache.cache(uuid, new CachedObject(name, Settings.USERNAME_EXPIRE));
    }

    public static String getUsername(UUID uuid){
        Object register = nameCache.getParsed(uuid);
        if (register == null) {
            return null;
        } else {
            return (String) register;
        }
    }

    public static UUID getUUID(String name){
        Object register = uuidCache.getParsed(name);
        if (register == null) {
            return null;
        } else {
            return (UUID) register;
        }
    }

    public static Cache<String> getUUIDCache(){
        return uuidCache;
    }

    public static Cache<UUID> getNameCache(){
        return nameCache;
    }
}
