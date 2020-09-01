package me.border.spigotutilities.mojang.utils;

import me.border.spigotutilities.cache.Cacheable;
import me.border.spigotutilities.cache.ICache;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class NameCache implements ICache<UUID> {
    // NAME BEING THE VALUE AND UUID BEING THE KEY
    private HashMap<UUID, Cacheable> cacheHashMap = new HashMap<>();

    public NameCache(){
        try {
            Thread threadCleanerUpper = new Thread(new Runnable() {
                int milliSecondSleepTime = 60000; // 60 Seconds
                public void run() {
                    try {
                        while (true) {
                            Set<UUID> keySet = cacheHashMap.keySet();
                            for (UUID key : keySet) {
                                Cacheable value = cacheHashMap.get(key);
                                if (value.isExpired()) {
                                    cacheHashMap.remove(key);
                                }
                            }
                            Thread.sleep(this.milliSecondSleepTime);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            threadCleanerUpper.setPriority(Thread.MIN_PRIORITY);
            threadCleanerUpper.start();
        } catch (Exception e) {
            System.out.println("ICache.Static Block: " + e);
        }
    }

    @Override
    public void cache(UUID key, Cacheable value) {
        cacheHashMap.put(key, value);
    }

    @Override
    public Cacheable getCache(UUID key) {
        Cacheable object = cacheHashMap.get(key);
        if (object == null)
            return null;
        if (object.isExpired()) {
            cacheHashMap.remove(key);
            return null;
        } else {
            return object;
        }
    }

    @Override
    public Object getParsedCache(UUID key) {
        return getCache(key).getObject();
    }

    @Override
    public void delete(UUID key) {
        cacheHashMap.remove(key);
    }
}
