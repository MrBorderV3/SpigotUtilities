package me.border.spigotutilities.task;

import me.border.spigotutilities.UtilsMain;
import me.border.utilities.interfaces.Builder;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

/**
 * A {@link Builder} class to allow easier creation of {@link BukkitRunnable} tasks
 */
public class TaskBuilder implements Builder<BukkitRunnable> {

    public static TaskBuilder builder(){
        return new TaskBuilder();
    }

    private Type type = Type.NORMAL;

    private long after;
    private long every;
    private Runnable runnable;
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

    public TaskBuilder run(Runnable runnable){
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

    public BukkitRunnable build(){
        return new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        };
    }

    public void run(){
        if (async) {
            switch (type) {
                case NORMAL:
                    build().runTaskAsynchronously(UtilsMain.getInstance());
                case LATER:
                    build().runTaskLaterAsynchronously(UtilsMain.getInstance(), after);
                case REPEATING:
                    build().runTaskTimerAsynchronously(UtilsMain.getInstance(), after, every);
            }
        } else {
            switch (type) {
                case NORMAL:
                    build().runTask(UtilsMain.getInstance());
                case LATER:
                    build().runTaskLater(UtilsMain.getInstance(), after);
                case REPEATING:
                    build().runTaskTimer(UtilsMain.getInstance(), after, every);
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
