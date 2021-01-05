package me.border.spigotutilities.task;

import me.border.spigotutilities.UtilsMain;
import me.border.utilities.interfaces.Builder;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

/**
 * A {@link Builder} class to allow easier creation of {@link BukkitRunnable} tasks
 */
public class TaskBuilder implements Builder<Void> {

    public static TaskBuilder builder(){
        return new TaskBuilder();
    }

    private Type type = Type.NORMAL;

    private long after;
    private long every;
    private BukkitRunnable runnable;
    private boolean async = false;

    private TaskBuilder(){ }

    public TaskBuilder async(){
        async = true;
        return this;
    }

    public TaskBuilder sync(){
        async = false;
        return this;
    }

    public TaskBuilder run(BukkitRunnable runnable){
        this.runnable = runnable;
        return this;
    }

    public TaskBuilder after(long after, TimeUnit tu){
        this.after = tu.toSeconds(after)*20;
        this.type = Type.LATER;
        return this;
    }

    public TaskBuilder every(long every, TimeUnit tu){
        this.every = tu.toSeconds(every)*20;
        this.type = Type.REPEATING;
        return this;
    }

    public TaskBuilder setType(Type type){
        this.type = type;
        return this;
    }

    public Void build(){
        return null;
    }

    public void run(){
        if (async) {
            switch (type) {
                case NORMAL:
                    runnable.runTaskAsynchronously(UtilsMain.getInstance());
                    break;
                case LATER:
                    runnable.runTaskLaterAsynchronously(UtilsMain.getInstance(), after);
                    break;
                case REPEATING:
                    runnable.runTaskTimerAsynchronously(UtilsMain.getInstance(), after, every);
                    break;
            }
        } else {
            switch (type) {
                case NORMAL:
                    runnable.runTask(UtilsMain.getInstance());
                    break;
                case LATER:
                    runnable.runTaskLater(UtilsMain.getInstance(), after);
                    break;
                case REPEATING:
                    runnable.runTaskTimer(UtilsMain.getInstance(), after, every);
                    break;
            }
        }
    }


    public Type getType(){
        return type;
    }

    public boolean isAsync() {
        return async;
    }

    public enum Type {
        NORMAL, LATER, REPEATING;
    }
}
