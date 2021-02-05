package me.border.spigotutilities.task;

import me.border.utilities.terminable.Terminable;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Extension of {@link BukkitRunnable} to add {@link Terminable} support.
 */
public abstract class SpigotTask extends BukkitRunnable implements Terminable {

    private volatile boolean closed = false;

    @Override
    public void close() throws Exception {
        validate();
        cancel();
        closed = true;
    }

    @Override
    public boolean isClosed() {
        return closed;
    }
}
