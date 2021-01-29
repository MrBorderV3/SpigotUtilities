package me.border.spigotutilities.mojang.api;

import me.border.spigotutilities.mojang.Settings;
import me.border.utilities.cache.CacheMap;
import me.border.utilities.cache.ExpiringCacheMap;

import java.util.UUID;

public class MojangCacheManager {

    private static CacheMap<String> uuidCache;
    private static CacheMap<UUID> nameCache;

    public static void init(){
        uuidCache = new ExpiringCacheMap<>();
        nameCache = new ExpiringCacheMap<>();
    }

    public static void updateCache(UUID uuid, String name){
        uuidCache.cache(name, uuid, Settings.UUID_EXPIRE);
        nameCache.cache(uuid, name, Settings.USERNAME_EXPIRE);
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

    public static CacheMap<String> getUUIDCache(){
        return uuidCache;
    }

    public static CacheMap<UUID> getNameCache(){
        return nameCache;
    }
}
