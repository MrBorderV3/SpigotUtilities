package me.border.spigotutilities.mojang.utils;

import me.border.spigotutilities.cache.AbstractCache;

public class UUIDCache extends AbstractCache<String> {

    public UUIDCache() {
        super(60000);
    }
}
