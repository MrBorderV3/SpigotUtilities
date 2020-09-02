package me.border.spigotutilities.mojang.utils;

import me.border.spigotutilities.cache.AbstractCache;

import java.util.UUID;

public class NameCache extends AbstractCache<UUID> {
    public NameCache(){
        super(60000);
    }
}
