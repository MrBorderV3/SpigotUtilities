package me.border.spigotutilities.event;

import com.google.common.base.Preconditions;
import javafx.util.Builder;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Builder class to allow creation of {@link Subscription}
 *
 * @param <T> - {@link Event} Type
 * @see Subscription
 * @see EventListener
 */
public class SubscriptionBuilder<T extends Event> implements Builder<Subscription<T>> {

    public static <T extends Event> SubscriptionBuilder<T> builder(){
        return new SubscriptionBuilder<>();
    }

    private final BiConsumer<Object, Throwable> DEFAULT_EXCEPTION_CONSUMER = (event, throwable) -> {
        Bukkit.getLogger().severe("[SpigotUtilities] Exception thrown whilst handling event: " + event.getClass().getName());
        throwable.printStackTrace();
    };

    private Class<T> eventClass;
    private final List<Consumer<T>> eventHandlers = new ArrayList<>();
    private EventPriority priority = EventPriority.NORMAL;

    private boolean handleSubclasses = false;

    private final List<Predicate<T>> filters = new ArrayList<>();

    private final List<BiPredicate<Subscription<T> ,T> > preExpiryTests = new ArrayList<>();
    private final List<BiPredicate<Subscription<T> ,T> > postExpiryTests = new ArrayList<>();

    private BiConsumer<? super T, Throwable> exceptionConsumer = DEFAULT_EXCEPTION_CONSUMER;

    private boolean ignoreCancelled = false;

    protected SubscriptionBuilder() {
    }

    /**
     * Set the {@link Event} this subscription should listen to
     *
     * @param eventClass The {@link Event} class the subscription should listen to.
     * @return The {@link SubscriptionBuilder} for chaining purposes
     */
    public SubscriptionBuilder<T> event(Class<T> eventClass){
        this.eventClass = eventClass;
        return this;
    }

    /**
     * Add a handler to the subscription
     *
     * @param handler The handler
     * @return The {@link SubscriptionBuilder} for chaining purposes
     */
    public SubscriptionBuilder<T> handler(Consumer<T> handler){
        this.eventHandlers.add(handler);
        return this;
    }

    /**
     * Add a {@link Collection} of handlers to the subscription
     *
     * @param handlers The handlers
     * @return The {@link SubscriptionBuilder} for chaining purposes
     */
    public SubscriptionBuilder<T> handlers(Collection<Consumer<T>> handlers){
        this.eventHandlers.addAll(handlers);
        return this;
    }

    /**
     * Set the {@link EventPriority} of the subscription
     *
     * @param priority The priority
     * @return The {@link SubscriptionBuilder} for chaining purposes
     * @throws NullPointerException If {@code priority} is null
     */
    public SubscriptionBuilder<T> priority(EventPriority priority){
        Objects.requireNonNull(priority, "priority can't be null");
        this.priority = priority;
        return this;
    }

    /**
     * Set whether the subscription should also handle subclass of the given {@link Event}
     *
     * @param handleSubclasses Whether it should handle subclasses
     * @return The {@link SubscriptionBuilder} for chaining purposes
     */
    public SubscriptionBuilder<T> handleSubclasses(boolean handleSubclasses){
        this.handleSubclasses = handleSubclasses;
        return this;
    }

    /**
     * Add {@link BiPredicate} test at the given test points.
     * If any test returns {@code true} the subscription unregisters and closes.
     * If {@link TestStage#PRE} is given then the test will execute before the event handlers.
     * If {@link TestStage#POST} is given then the test will execute after the event handlers.
     * Both {@link TestStage}s can be used for the same test.
     *
     * @param test The {@link BiPredicate} test to add
     * @param testPoints The {@link TestStage}s to do the test at
     * @return The {@link SubscriptionBuilder} for chaining purposes
     */
    public SubscriptionBuilder<T> expireIf(BiPredicate<Subscription<T> ,T> test, TestStage... testPoints) {
        for (TestStage testPoint : testPoints) {
            switch (testPoint) {
                case PRE:
                    this.preExpiryTests.add(test);
                    break;
                case POST:
                    this.postExpiryTests.add(test);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown TestPoint: " + testPoint);
            }
        }

        return this;
    }

    /**
     * Add a collection of {@link BiPredicate} tests at the given test points.
     * @see SubscriptionBuilder#expireIf(BiPredicate, TestStage...)
     *
     * @param tests The {@link BiPredicate} tests to add
     * @param testPoints The {@link TestStage}s to do the tests at
     * @return The {@link SubscriptionBuilder} for chaining purposes
     */
    public SubscriptionBuilder<T> expireIf(Collection<BiPredicate<Subscription<T> ,T>> tests, TestStage... testPoints) {
        for (TestStage testPoint : testPoints) {
            switch (testPoint) {
                case PRE:
                    this.preExpiryTests.addAll(tests);
                    break;
                case POST:
                    this.postExpiryTests.addAll(tests);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown TestPoint: " + testPoint);
            }
        }

        return this;
    }

    /**
     * Add a duration test for the subscription to expire after given duration in the given {@link TimeUnit}.
     * This test is executed at {@link TestStage#PRE}
     *
     * @param duration The duration until the subscription expires
     * @param unit {@link TimeUnit} to parse the duration in
     * @return The {@link SubscriptionBuilder} for chaining purposes
     */
    public SubscriptionBuilder<T> expireAfter(long duration, TimeUnit unit) {
        Preconditions.checkArgument(duration >= 1, "duration cannot be 0 or under");
        long expiry = Math.addExact(System.currentTimeMillis(), unit.toMillis(duration));
        return expireIf((handler, event) -> System.currentTimeMillis() > expiry, TestStage.PRE);
    }

    /**
     * Add a max calls test for the subscription to expire after it has been used more than {@code maxCalls}.
     * This test is executed at {@link TestStage#PRE} and {@link TestStage#POST}
     *
     * @param maxCalls The maximum amount of calls until the subscription expires
     * @return The {@link SubscriptionBuilder} for chaining purposes
     */
    public SubscriptionBuilder<T> expireAfter(long maxCalls) {
        Preconditions.checkArgument(maxCalls >= 1, "maxCalls cannot be 0 or under");
        return expireIf((handler, event) -> handler.getCalls() >= maxCalls, TestStage.PRE, TestStage.POST);
    }

    /**
     * Set the {@link BiConsumer} that will be used in-case any {@link Throwable} is thrown during the event execution
     *
     * @param exceptionConsumer The {@link BiConsumer} to use
     * @return The {@link SubscriptionBuilder} for chaining purposes
     * @throws NullPointerException if {@code exceptionConsumer} is null.
     */
    public SubscriptionBuilder<T> exceptionConsumer(BiConsumer<? super T, Throwable> exceptionConsumer){
        Objects.requireNonNull(exceptionConsumer, "exceptionConsumer cannot be null");
        this.exceptionConsumer = exceptionConsumer;
        return this;
    }

    /**
     * Set whether the subscription should pass cancelled events.
     * If ignoreCancelled is true and the event is cancelled, the method is not called. Otherwise, the method is always called.
     *
     * @param ignoreCancelled Whether the subscription should pass cancelled events.
     * @return The {@link SubscriptionBuilder} for chaining purposes
     */
    public SubscriptionBuilder<T> ignoreCancelled(boolean ignoreCancelled){
        this.ignoreCancelled = ignoreCancelled;
        return this;
    }

    @Override
    public Subscription<T> build() {
        Preconditions.checkArgument(!eventHandlers.isEmpty(), "handlers cannot be empty");
        EventListener<T> eventListener = new EventListener<>(eventClass, eventHandlers, priority, handleSubclasses, filters, preExpiryTests, postExpiryTests, exceptionConsumer);
        eventListener.register(ignoreCancelled);
        return eventListener;
    }
}
