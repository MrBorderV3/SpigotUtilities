package me.border.spigotutilities.mojang.utils;

import me.border.spigotutilities.cache.Cacheable;
import me.border.spigotutilities.cache.ICache;

import java.util.HashMap;
import java.util.Set;

public class UUIDCache implements ICache<String> {
    // UUID BEING THE VALUE AND NAME BEING THE KEY
    private HashMap<String, Cacheable> cacheHashMap = new HashMap<>();

    public UUIDCache(){
        try {
            Thread threadCleanerUpper = new Thread(new Runnable() {
                int milliSecondSleepTime = 60000; // 60 Seconds
                public void run() {
                    try {
                        while (true) {
                            Set<String> keySet = cacheHashMap.keySet();
                            for (String key : keySet) {
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
    public void cache(String key, Cacheable value) {
        cacheHashMap.put(key, value);
    }

    @Override
    public Cacheable getCache(String key) {
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
    public Object getParsedCache(String key) {
        return getCache(key).getObject();
    }

    @Override
    public void delete(String key) {
        cacheHashMap.remove(key);
    }
}
