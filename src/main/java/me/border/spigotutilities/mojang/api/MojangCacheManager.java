package me.border.spigotutilities.mojang.api;

import me.border.spigotutilities.cache.CachedObject;
import me.border.spigotutilities.mojang.utils.NameCache;
import me.border.spigotutilities.mojang.utils.UUIDCache;

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
        return (register == null ? null : (String) register);
    }

    public static UUID getUUID(String name){
        Object register = uuidCache.getParsedCache(name);
        return (register == null ? null : (UUID) register);
    }

    public static UUIDCache getUUIDCache(){
        return uuidCache;
    }

    public static NameCache getNameCache(){
        return nameCache;
    }
}
