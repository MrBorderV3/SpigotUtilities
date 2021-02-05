package me.border.spigotutilities.task;

import me.border.spigotutilities.UtilsMain;
import me.border.utilities.interfaces.Builder;
import me.border.utilities.terminable.composite.CompositeTerminable;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * A {@link Builder} class to allow easier creation of {@link SpigotTask} tasks
 */
public class SpigotTaskBuilder implements Builder<SpigotTask> {

    public static SpigotTaskBuilder builder(){
        return new SpigotTaskBuilder();
    }

    private Type type = Type.NORMAL;

    private boolean async = false;

    private boolean bind = false;
    private Set<CompositeTerminable> compositeTerminables;

    private long after;
    private long every;
    private SpigotTask task;

    private SpigotTaskBuilder(){ }

    public SpigotTaskBuilder async(){
        async = true;
        return this;
    }

    public SpigotTaskBuilder sync(){
        async = false;
        return this;
    }

    public SpigotTaskBuilder task(SpigotTask task){
        this.task = task;
        return this;
    }

    public SpigotTaskBuilder after(long ticks){
        this.after = ticks;
        this.type = Type.LATER;
        return this;
    }

    public SpigotTaskBuilder every(long ticks){
        this.every = ticks;
        this.type = Type.REPEATING;
        return this;
    }

    public SpigotTaskBuilder after(long after, TimeUnit tu){
        this.after = tu.toSeconds(after)*20;
        this.type = Type.LATER;
        return this;
    }

    public SpigotTaskBuilder every(long every, TimeUnit tu){
        this.every = tu.toSeconds(every)*20;
        this.type = Type.REPEATING;
        return this;
    }

    public SpigotTaskBuilder setType(Type type){
        this.type = type;
        return this;
    }

    public SpigotTaskBuilder bind(CompositeTerminable compositeTerminable){
        if (!bind) {
            this.bind = true;
            this.compositeTerminables = new HashSet<>();
        }
        this.compositeTerminables.add(compositeTerminable);
        return this;
    }

    public SpigotTaskBuilder unbind(CompositeTerminable compositeTerminable){
        if (!bind)
            return this;
        this.compositeTerminables.remove(compositeTerminable);
        if (this.compositeTerminables.isEmpty())
            bind = false;
        return this;
    }

    public SpigotTask build(){
        if (bind){
            compositeTerminables.forEach(ct -> ct.bind(task));
        }
        if (async) {
            switch (type) {
                case NORMAL:
                    task.runTaskAsynchronously(UtilsMain.getInstance());
                    break;
                case LATER:
                    task.runTaskLaterAsynchronously(UtilsMain.getInstance(), after);
                    break;
                case REPEATING:
                    task.runTaskTimerAsynchronously(UtilsMain.getInstance(), after, every);
                    break;
            }
        } else {
            switch (type) {
                case NORMAL:
                    task.runTask(UtilsMain.getInstance());
                    break;
                case LATER:
                    task.runTaskLater(UtilsMain.getInstance(), after);
                    break;
                case REPEATING:
                    task.runTaskTimer(UtilsMain.getInstance(), after, every);
                    break;
            }
        }

        return task;
    }

    public Type getType(){
        return type;
    }

    public enum Type {
        NORMAL, LATER, REPEATING;
    }
}
