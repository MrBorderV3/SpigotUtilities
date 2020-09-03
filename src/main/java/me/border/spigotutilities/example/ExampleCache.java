package me.border.spigotutilities.example;

import me.border.spigotutilities.cache.AbstractCache;

public class ExampleCache extends AbstractCache<String> {
    protected ExampleCache(){
        super(60000); // In millis
    }
}
