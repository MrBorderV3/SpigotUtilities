package me.border.spigotutilities.config;

import me.border.spigotutilities.plugin.SpigotPlugin;
import me.border.spigotutilities.plugin.UtilsMain;
import me.border.utilities.cache.CacheMap;
import me.border.utilities.cache.impl.ExpiringCacheMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * This class is used to allow easier and better access of {@link SpigotPlugin#getConfig()}.
 * This class uses {@link CacheMap} to reduce the amount of calls being made to the config.
 * If it must call the config it will update the received data in the {@link CacheMap} beforehand for easier access if the variable is queued again.
 */
public class ConfigCache {

    private static ConfigCache instance;

    private static final JavaPlugin plugin = UtilsMain.getInstance();

    private final ExpiringCacheMap<String> configCache = new ExpiringCacheMap<>(1, TimeUnit.MINUTES);

    private ConfigCache(){

    }

    public String getString(String path){
        if (configCache.containsKey(path)) {
            Object temp = get(path);
            if (temp instanceof String)
                return (String) temp;

            throw new IllegalArgumentException("Path \"" + path + "\" is not a String list!");
        }

        String temp = plugin.getConfig().getString(path);
        cache(path, temp);
        return temp;
    }

    public List<String> getStringList(String path){
        if (configCache.containsKey(path)) {
            Object temp = get(path);
            if (temp instanceof List<?>)
                return (List<String>) temp;

            throw new IllegalArgumentException("Path \"" + path + "\" is not a String list!");
        }

        List<String> temp = plugin.getConfig().getStringList(path);
        cache(path, temp);
        return temp;
    }

    public int getInt(String path){
        if (configCache.containsKey(path)) {
            Object temp = get(path);
            if (temp instanceof Integer)
                return (int) temp;

            throw new IllegalArgumentException("Path \"" + path + "\" is not an integer!");
        }

        int temp = plugin.getConfig().getInt(path);
        cache(path, temp);
        return temp;
    }

    public double getDouble(String path){
        if (configCache.containsKey(path)) {
            Object temp = get(path);
            if (temp instanceof Double)
                return (double) temp;

            throw new IllegalArgumentException("Path \"" + path + "\" is not a double!");
        }

        double temp = plugin.getConfig().getDouble(path);
        cache(path, temp);
        return temp;

    }

    public long getLong(String path){
        if (configCache.containsKey(path)) {
            Object temp = get(path);
            if (temp instanceof Long)
                return (long) temp;

            throw new IllegalArgumentException("Path \"" + path + "\" is not a long!");
        }

        long temp = plugin.getConfig().getLong(path);
        cache(path, temp);
        return temp;
    }

    public boolean getBoolean(String path){
        if (configCache.containsKey(path)) {
            Object temp = get(path);
            if (temp instanceof Boolean)
                return (boolean) temp;

            throw new IllegalArgumentException("Path \"" + path + "\" is not a long!");
        }

        boolean temp = plugin.getConfig().getBoolean(path);
        cache(path, temp);
        return temp;
    }

    public Object get(String path){
        if (configCache.containsKey(path))
            return configCache.getParsed(path);

        Object temp = plugin.getConfig().get(path);
        cache(path, temp);
        return temp;
    }

    public void clear(){
        configCache.clear();
    }

    private void cache(String path, Object obj){
        Objects.requireNonNull(obj, "Object cannot be null!");
        Objects.requireNonNull(path, "Path cannot be null!");
        configCache.cache(path, obj, 10);
    }

    public static ConfigCache getInstance() {
        if (instance == null)
            instance = new ConfigCache();

        return instance;
    }
}
