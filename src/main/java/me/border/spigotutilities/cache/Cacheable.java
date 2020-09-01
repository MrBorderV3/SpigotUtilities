package me.border.spigotutilities.cache;

public interface Cacheable {

    /**
     * Check if the cache is expired
     *
     * @return If it expired
     */
    boolean isExpired();

    /**
     * Get the cached object
     *
     * @return The cached object
     */
    Object getObject();

    int hashCode();

    boolean equals(Object o);
}
