package me.border.spigotutilities.cache;

public interface ICache<K> {

    /**
     * Cache a cacheable object
     *
     * @param key Object identifier
     * @param value The cacheable object to cache
     */
    void cache(K key, Cacheable value);

    /**
     * Get a cacheable object from the cache
     *
     * @param key The identifier
     * @return The object, may be null
     */
    Cacheable getCache(K key);

    /**
     * Get a parsed cacheable object from the cache
     *
     * @param key the identifier
     * @return The object, may be null
     */
    Object getParsedCache(K key);

    /**
     * Delete an object from the cache
     *
     * @param key The object key
     */
    void delete(K key);
}
