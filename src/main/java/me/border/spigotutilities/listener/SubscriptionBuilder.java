package me.border.spigotutilities.listener;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

public class SubscriptionBuilder<T extends Event> {

    private final BiConsumer<Object, Throwable> DEFAULT_EXCEPTION_CONSUMER = (event, throwable) -> {
        Bukkit.getLogger().severe("[SpigotUtilities] Exception thrown whilst handling event: " + event.getClass().getName());
        throwable.printStackTrace();
    };

    public SubscriptionBuilder<T> expireAfter(long duration, TimeUnit unit) {
        Objects.requireNonNull(unit, "unit");
        Preconditions.checkArgument(duration >= 1, "duration < 1");
        long expiry = Math.addExact(System.currentTimeMillis(), unit.toMillis(duration));
        return expireIf((handler, event) -> System.currentTimeMillis() > expiry, ExpiryTestStage.PRE);
    }

    public SubscriptionBuilder<T> expireAfter(long maxCalls) {
        Preconditions.checkArgument(maxCalls >= 1, "maxCalls < 1");
        return expireIf((handler, event) -> handler.getCallCounter() >= maxCalls, ExpiryTestStage.PRE, ExpiryTestStage.POST_HANDLE);
    }
}
