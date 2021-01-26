package me.border.spigotutilities.utils.serializable;

import org.bukkit.Location;

import java.io.Serializable;
import java.util.Objects;

/**
 * Utility class to easily serialize {@link Location}
 */
public class SerializableLocation implements Serializable {

    private final double x;
    private final double y;
    private final double z;


    public SerializableLocation(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public SerializableLocation(Location location){
        this(location.getX(), location.getY(), location.getZ());
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
}
