package me.border.spigotutilities.plugin;

import me.border.spigotutilities.UtilsMain;
import me.border.spigotutilities.file.AbstractSpigotYamlFile;
import me.border.spigotutilities.task.TaskBuilder;
import me.border.utilities.file.AbstractSerializedFile;
import me.border.utilities.terminable.composite.CompositeTerminable;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.EnumSet;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * An extension of {@link JavaPlugin} that adds additional features and automates certain features.
 *
 * {@link Setting} Allows for automation of certain features. Settings should only be added in the {@link #load()} method.
 *
 * {@code terminableRegistry} Allows for binding of {@link AutoCloseable}s using the {@link #bind(AutoCloseable)} method. The closeables are
 * {@link CompositeTerminable#cleanup()}ed every 30 seconds and closed during the {@link #onDisable()}.
 */
public abstract class SpigotPlugin extends JavaPlugin {
    private static SpigotPlugin instance;

    private EnumSet<Setting> settings;
    private CompositeTerminable terminableRegistry;

    public SpigotPlugin(){
        super();
    }

    protected void load() {}
    protected void enable() {}
    protected void disable() {}

    @Override
    public void onEnable() {
        instance = this;
        UtilsMain.init(instance, settings.contains(Setting.PLAYER_INFO));
        TaskBuilder.builder()
                .async()
                .after(15, TimeUnit.SECONDS)
                .every(30, TimeUnit.SECONDS)
                .runnable(new BukkitRunnable() {
                    @Override
                    public void run() {
                        terminableRegistry.cleanup();
                    }
                })
                .run();
        if (settings.contains(Setting.SETUP_RESOURCES)){
            AbstractSpigotYamlFile.setupAll();
            AbstractSerializedFile.setupAll();
        }

        enable();
    }

    @Override
    public void onLoad() {
        this.terminableRegistry = CompositeTerminable.create();
        this.settings = EnumSet.noneOf(Setting.class);

        load();
    }

    @Override
    public void onDisable() {
        disable();
        this.terminableRegistry.closeSilently();
        if (settings.contains(Setting.SAVE_RESOURCES)) {
            AbstractSpigotYamlFile.saveAll();
            AbstractSerializedFile.saveAll();
        }
    }

    /**
     * Bind a terminable to {@code terminableRegistry}
     *
     * @param terminable The terminable to bind.
     * @param <T> The type. Must extend {@link AutoCloseable}.
     * @return The given parameter for chaining.
     *
     * @see CompositeTerminable
     */
    public <T extends AutoCloseable> T bind(T terminable) {
        return this.terminableRegistry.bind(terminable);
    }

    /**
     * Util method to register a listener.
     *
     * @param listener The listener to register.
     */
    public void registerListener(Listener listener){
        Objects.requireNonNull(listener, "listener");
        getServer().getPluginManager().registerEvents(listener, this);
    }

    /**
     * Util method to register a {@link CommandExecutor}
     *
     * @param executor The command executor.
     * @param cmd The name of the command.
     */
    public void registerCommand(CommandExecutor executor, String cmd){
        getCommand(cmd).setExecutor(executor);
    }

    /**
     * Check if a plugin exists and is enabled on the server
     *
     * @param name The name of the plugin
     * @return Whether it exists
     */
    public boolean isPluginPresent(String name) {
        return getServer().getPluginManager().getPlugin(name) != null;
    }

    /**
     * Get a relative {@link File} in the plugin's folder.
     *
     * @param name The name of the file.
     * @return The file.
     */
    public File getRelativeFile(String name) {
        if (!getDataFolder().exists())
            getDataFolder().mkdirs();
        return new File(getDataFolder(), name);
    }

    /**
     * Get the plugin's settings.
     *
     * @return The settings.
     */
    public EnumSet<Setting> getSettings() {
        return settings;
    }

    /**
     * Get an instance of the plugin.
     * Only one instance can be used at a time since there can only be one active {@link JavaPlugin} per plugin.
     *
     * @return The instance.
     */
    public static SpigotPlugin getInstance() {
        return instance;
    }
}
