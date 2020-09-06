package me.border.spigotutilities.mojang.cache;

import me.border.utilities.cache.AbstractCache;

import java.util.UUID;

public class NameCache extends AbstractCache<UUID> {
    public NameCache(){
        super(60000);
    }
}
