package me.border.spigotutilities.cache;

import java.util.HashMap;
import java.util.Set;

public abstract class AbstractCache<K> implements ICache<K> {
    protected HashMap<K, Cacheable> cacheHashMap = new HashMap<>();

    // START THE CLEANUP THREAD AND RUN IT EVERY CHOSEN TIME.
    public AbstractCache(int sleepTime){
        try {
            Thread threadCleanerUpper = new Thread(new Runnable() {
                int milliSecondSleepTime = sleepTime;
                public void run() {
                    try {
                        while (true) {
                            Set<K> keySet = cacheHashMap.keySet();
                            for (K key : keySet) {
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
            e.printStackTrace();
        }
    }

    // Cache an object
    @Override
    public void cache(K key, Cacheable value) {
        cacheHashMap.put(key, value);
    }

    // Get a Cacheable object from the cache with the corresponding key
    @Override
    public Cacheable getCache(K key) {
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

    // Get a parsed object from the cache with the corresponding key
    @Override
    public Object getParsedCache(K key) {
        return getCache(key).getObject();
    }

    // Delete an object from the cache with the corresponding key
    @Override
    public void delete(K key) {
        cacheHashMap.remove(key);
    }
}
