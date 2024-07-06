package me.border.spigotutilities.serialize;

import me.border.utilities.cache.Cacheable;
import me.border.utilities.cache.impl.ExpiringCacheMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Utility class to easily serialize {@link Location}
 */
public class SerializableLocation implements Serializable {

    private static final ExpiringCacheMap<Integer> locationCache = new ExpiringCacheMap<>(60, TimeUnit.SECONDS);

    private final String world;
    private final double x;
    private final double y;
    private final double z;

    private int hash;

    /**
     * Create a new {@link SerializableLocation}
     *
     * @param x The x
     * @param y The y
     * @param z The z
     * @return The matching {@link SerializableLocation}
     */
    public static SerializableLocation create(String world, double x, double y, double z){
        int hash = hashParams(world, x, y, z);
        Cacheable cacheable = locationCache.get(hash);
        if (cacheable == null) {
            SerializableLocation serializableLocation = new SerializableLocation(world, x, y, z);
            locationCache.cache(hash, serializableLocation, 15);
            return serializableLocation;
        }

        return (SerializableLocation) cacheable.getObject();
    }


    /**
     * Create a new {@link SerializableLocation} or receive one from the cache if exists
     *
     * @param location The {@link} Location
     * @return The matching {@link SerializableLocation}
     */
    public static SerializableLocation create(Location location) {
        return create(location.getWorld().getName(), location.getX(), location.getY(), location.getZ());
    }

    private SerializableLocation(String world, double x, double y, double z){
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }


    public Location toLocation(){
        return new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SerializableLocation that = (SerializableLocation) o;
        return that.world.equals(world) && Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0 && Double.compare(that.z, z) == 0;
    }

    @Override
    public int hashCode() {
        if (this.hash == 0)
            this.hash = hashParams(world, x, y, z);

        return hash;
    }

    private static int hashParams(String world, double x, double y, double z){
        int hash = 3;

        hash = 19 * hash + (int) (Double.doubleToLongBits(x) ^ (Double.doubleToLongBits(x) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(y) ^ (Double.doubleToLongBits(y) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(z) ^ (Double.doubleToLongBits(z) >>> 32));
        hash = hash + world.hashCode();

        return hash;
    }

}
