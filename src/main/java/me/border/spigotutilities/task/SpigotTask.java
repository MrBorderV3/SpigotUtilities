package me.border.spigotutilities.task;

import me.border.utilities.terminable.Terminable;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Extension of {@link BukkitRunnable} to add {@link Terminable} support.
 */
public abstract class SpigotTask extends BukkitRunnable implements Terminable {

    private static final Map<Integer, SpigotTask> activeTasks = new HashMap<>();

    private volatile boolean closed = false;

    protected SpigotTask(){
        super();
    }

    @Override
    public synchronized void close() {
        validate();
        activeTasks.remove(getTaskId());
        super.cancel();
        closed = true;
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        close();
    }

    @Override
    public synchronized boolean isClosed() {
        return closed;
    }

    @NotNull
    @Override
    public synchronized BukkitTask runTaskTimerAsynchronously(@NotNull Plugin plugin, long delay, long period) throws IllegalArgumentException, IllegalStateException {
        validate();
        BukkitTask task = super.runTaskTimerAsynchronously(plugin, delay, period);
        activeTasks.put(task.getTaskId(), this);
        return task;
    }

    @NotNull
    @Override
    public synchronized BukkitTask runTaskTimer(@NotNull Plugin plugin, long delay, long period) throws IllegalArgumentException, IllegalStateException {
        validate();
        BukkitTask task = super.runTaskTimer(plugin, delay, period);
        activeTasks.put(task.getTaskId(), this);
        return task;
    }

    @NotNull
    @Override
    public synchronized BukkitTask runTaskLaterAsynchronously(@NotNull Plugin plugin, long delay) throws IllegalArgumentException, IllegalStateException {
        validate();
        BukkitTask task = super.runTaskLaterAsynchronously(plugin, delay);
        activeTasks.put(task.getTaskId(), this);
        return task;
    }

    @NotNull
    @Override
    public synchronized BukkitTask runTaskLater(@NotNull Plugin plugin, long delay) throws IllegalArgumentException, IllegalStateException {
        validate();
        BukkitTask task = super.runTaskLater(plugin, delay);
        activeTasks.put(task.getTaskId(), this);
        return task;
    }

    @NotNull
    @Override
    public synchronized BukkitTask runTaskAsynchronously(@NotNull Plugin plugin) throws IllegalArgumentException, IllegalStateException {
        validate();
        BukkitTask task = super.runTaskAsynchronously(plugin);
        activeTasks.put(task.getTaskId(), this);
        return task;
    }

    @NotNull
    @Override
    public synchronized BukkitTask runTask(@NotNull Plugin plugin) throws IllegalArgumentException, IllegalStateException {
        validate();
        BukkitTask task = super.runTask(plugin);
        activeTasks.put(task.getTaskId(), this);
        return task;
    }

    public static Map<Integer, SpigotTask> getActiveTasks() {
        return activeTasks;
    }
}
