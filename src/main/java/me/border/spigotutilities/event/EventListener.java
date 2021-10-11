package me.border.spigotutilities.event;

import me.border.spigotutilities.plugin.SpigotPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.*;
import org.bukkit.plugin.EventExecutor;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EventListener<T extends Event> implements Subscription<T>, EventExecutor, Listener {

    private final Class<T> eventClass;
    private final List<Consumer<T>> eventHandlers;
    private final EventPriority priority;

    private final boolean handleSubclasses;

    private final List<Predicate<T>> filters;

    private final List<BiPredicate<Subscription<T> ,T>> preExpiryTest;
    private final List<BiPredicate<Subscription<T> ,T>> postExpiryTest;

    private final BiConsumer<? super T, Throwable> exceptionConsumer;

    private final AtomicLong calls = new AtomicLong(0);
    private final AtomicBoolean active = new AtomicBoolean(true);

    protected EventListener(Class<T> eventClass, List<Consumer<T>> eventHandlers, EventPriority priority, boolean handleSubclasses,
                         List<Predicate<T>> filters, List<BiPredicate<Subscription<T> ,T> > preExpiryTest, List<BiPredicate<Subscription<T> ,T> > postExpiryTest, BiConsumer<? super T, Throwable> exceptionConsumer){
        this.eventClass = eventClass;
        this.eventHandlers = eventHandlers;
        this.priority = priority;

        this.handleSubclasses = handleSubclasses;

        this.filters = filters;

        this.preExpiryTest = preExpiryTest;
        this.postExpiryTest = postExpiryTest;

        this.exceptionConsumer = exceptionConsumer;
    }

    @Override
    public void execute(@NotNull Listener listener, @NotNull Event event) throws EventException {
        if (this.handleSubclasses){
            if (!this.eventClass.isInstance(event))
                return;
        } else {
            if (event.getClass() != this.eventClass)
                return;
        }

        if (!this.active.get()){
            event.getHandlers().unregister(listener);
            return;
        }

        T eventInstance = this.eventClass.cast(event);

        for (BiPredicate<Subscription<T> ,T>  test : this.preExpiryTest){
            if (test.test(this, eventInstance)){
                event.getHandlers().unregister(listener);
                this.active.set(false);
                return;
            }
        }

        try {
            for (Predicate<T> filter : this.filters) {
                if (!filter.test(eventInstance))
                    return;
            }

            eventHandlers.forEach(handler -> {
                try {
                    handler.accept(eventInstance);
                } catch (Throwable t) {
                    exceptionConsumer.accept(eventInstance, t);
                }
            });
            this.calls.incrementAndGet();
        } catch (Throwable t){
            exceptionConsumer.accept(eventInstance, t);
        }

        for (BiPredicate<Subscription<T> ,T>  test : this.postExpiryTest){
            if (test.test(this, eventInstance)){
                event.getHandlers().unregister(listener);
                this.active.set(false);
                return;
            }
        }
    }

    public void register(boolean ignoreCancelled){
        validate();
        Bukkit.getPluginManager().registerEvent(this.eventClass, this, this.priority, this, SpigotPlugin.getInstance(), ignoreCancelled);
    }

    @Override
    public void unregister() {
        validate();
        if (!this.active.getAndSet(false))
            return;

        unregisterListener(this.eventClass, this);
    }

    @Override
    public boolean isActive(){
        return active.get();
    }

    @Override
    public long getCalls(){
        return calls.get();
    }

    @Override
    public Class<T> getEventClass() {
        return eventClass;
    }

    private static void unregisterListener(Class<? extends Event> eventClass, Listener listener) {
        try {
            Method getHandlerListMethod = eventClass.getMethod("getHandlerList");
            HandlerList handlerList = (HandlerList) getHandlerListMethod.invoke(null);
            handlerList.unregister(listener);
        } catch (Throwable ignored) {
        }
    }
}
