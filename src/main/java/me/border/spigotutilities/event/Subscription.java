package me.border.spigotutilities.event;

import me.border.utilities.terminable.Terminable;
import org.bukkit.event.Event;

/**
 * Represents a subscription to a given {@link Event}(s).
 */
public interface Subscription<T extends Event> extends Terminable {

    /**
     * Unregister the subscription
     */
    void unregister();

    /**
     * Get whether the subscription is active
     *
     * @return If the subscription is active
     */
    boolean isActive();

    /**
     * Get the amount of times the subscription has been called
     *
     * @return the amount of times the subscription has been called
     */
    long getCalls();

    /**
     * Get the {@link Event} class the subscription is handling
     *
     * @return The class the subscription is handling
     */
    Class<T> getEventClass();

    @Override
    default void close() throws Exception {
        validate();
        unregister();
    }

    @Override
    default boolean isClosed() {
        return !isActive();
    }
}
