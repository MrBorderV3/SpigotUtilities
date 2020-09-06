package me.border.spigotutilities.mojang.api;

import me.border.spigotutilities.mojang.cache.NameCache;
import me.border.spigotutilities.mojang.utils.Settings;
import me.border.spigotutilities.mojang.cache.UUIDCache;
import me.border.utilities.cache.CachedObject;

import java.util.UUID;

public class MojangCacheManager {

    private static UUIDCache uuidCache = new UUIDCache();
    private static NameCache nameCache = new NameCache();

    public static void updateCache(UUID uuid, String name){
        uuidCache.cache(name, new CachedObject(uuid, Settings.UUID_EXPIRE));
        nameCache.cache(uuid, new CachedObject(name, Settings.USERNAME_EXPIRE));
    }

    public static String getUsername(UUID uuid){
        Object register = nameCache.getParsedCache(uuid);
        if (register == null) {
            return null;
        } else {
            return (String) register;
        }
    }

    public static UUID getUUID(String name){
        Object register = uuidCache.getParsedCache(name);
        if (register == null) {
            return null;
        } else {
            return (UUID) register;
        }
    }

    public static UUIDCache getUUIDCache(){
        return uuidCache;
    }

    public static NameCache getNameCache(){
        return nameCache;
    }
}
