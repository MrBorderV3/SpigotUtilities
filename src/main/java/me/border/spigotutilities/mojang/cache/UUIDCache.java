package me.border.spigotutilities.mojang.cache;

import me.border.utilities.cache.AbstractCache;

public class UUIDCache extends AbstractCache<String> {
    public UUIDCache() {
        super(60000);
    }
}
