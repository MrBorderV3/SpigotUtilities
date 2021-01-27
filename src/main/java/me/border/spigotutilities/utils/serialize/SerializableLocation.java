package me.border.spigotutilities.utils.serialize;

import me.border.utilities.cache.Cache;
import me.border.utilities.cache.Cacheable;
import me.border.utilities.cache.CachedObject;
import me.border.utilities.cache.ExpiringCache;
import org.bukkit.Location;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Utility class to easily serialize {@link Location}
 */
public class SerializableLocation implements Serializable {

    private static final Cache<Integer> locationCache = new ExpiringCache<>(60, TimeUnit.SECONDS);

    private final double x;
    private final double y;
    private final double z;

    /**
     * Create a new {@link SerializableLocation}
     *
     * @param x The x
     * @param y The y
     * @param z The z
     * @return The matching {@link SerializableLocation}
     */
    public static SerializableLocation create(double x, double y, double z){
        int hash = hashParams(x, y, z);
        Cacheable cacheable = locationCache.get(hash);
        if (cacheable == null) {
            SerializableLocation serializableLocation = new SerializableLocation(x, y, z);
            locationCache.cache(hash, new CachedObject(serializableLocation, 15));
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
        return create(location.getX(), location.getY(), location.getZ());
    }

    private SerializableLocation(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SerializableLocation that = (SerializableLocation) o;
        return Double.compare(that.x, x) == 0 && Double.compare(that.y, y) == 0 && Double.compare(that.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    private static int hashParams(double x, double y, double z){
        int hash = 3;

        hash = 19 * hash + (int) (Double.doubleToLongBits(x) ^ (Double.doubleToLongBits(x) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(y) ^ (Double.doubleToLongBits(y) >>> 32));
        hash = 19 * hash + (int) (Double.doubleToLongBits(z) ^ (Double.doubleToLongBits(z) >>> 32));
        return hash;
    }

}
