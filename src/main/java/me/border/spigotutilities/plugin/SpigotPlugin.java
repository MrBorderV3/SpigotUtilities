package me.border.spigotutilities.plugin;

import me.border.spigotutilities.PlayerInfoAdapter;
import me.border.spigotutilities.command.ICommand;
import me.border.spigotutilities.config.ConfigCache;
import me.border.spigotutilities.file.AbstractSpigotYamlFile;
import me.border.spigotutilities.task.SpigotTask;
import me.border.spigotutilities.task.SpigotTaskBuilder;
import me.border.utilities.file.AbstractSerializedFile;
import me.border.utilities.terminable.composite.CompositeTerminable;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
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

    private boolean saveEntities;
    private List<Command> commands;
    private List<Listener> listeners;

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
        UtilsMain.init(instance);

        SpigotTaskBuilder.builder()
                .async()
                .after(15, TimeUnit.SECONDS)
                .every(30, TimeUnit.SECONDS)
                .task(new SpigotTask() {
                    @Override
                    public void run() {
                        terminableRegistry.cleanup();
                    }
                })
                .bind(this.terminableRegistry)
                .build();

        if (settings.contains(Setting.SETUP_RESOURCES)){
            AbstractSpigotYamlFile.setupAll();
            AbstractSerializedFile.setupAll();
        }

        if (settings.contains(Setting.PLAYER_INFO)) {
            PlayerInfoAdapter.init();
        }

        enable();
    }

    @Override
    public void onLoad() {
        UtilsMain.setUsingSpigotPlugin(true);
        this.terminableRegistry = CompositeTerminable.create();
        this.settings = EnumSet.noneOf(Setting.class);

        load();

        this.saveEntities = !settings.contains(Setting.DISABLE_ENTITY_REFERENCE);
        if (saveEntities){
            commands = new ArrayList<>();
            listeners = new ArrayList<>();
        }

        if (this.settings.contains(Setting.DISABLE_CONFIG_CACHE)){
            UtilsMain.setConfigCache(false);
        }
    }

    @Override
    public void onDisable() {
        disable();
        this.terminableRegistry.closeSilently();
        if (settings.contains(Setting.SAVE_RESOURCES)) {
            AbstractSpigotYamlFile.saveAll();
            AbstractSerializedFile.saveAll();
        }

        UtilsMain.setUsingSpigotPlugin(false);
    }

    /**
     * Util method to register a listener.
     *
     * @param listener The listener to register.
     */
    public void registerListener(Listener listener){
        getServer().getPluginManager().registerEvents(listener, this);
        if (saveEntities)
            listeners.add(listener);
    }

    /**
     * Util method to register a {@link CommandExecutor}
     *
     * @param executor The command executor.
     * @param cmd The name of the command.
     */
    public void registerCommand(CommandExecutor executor, String cmd){
        PluginCommand pluginCommand = getCommand(cmd);
        pluginCommand.setExecutor(executor);
        if (saveEntities)
            commands.add(pluginCommand);
    }

    /**
     * Util method to register a {@link ICommand}
     * Only use this if {@link me.border.spigotutilities.plugin.Setting#DISABLE_ENTITY_REFERENCE} is not used
     *
     * @param cmd The command.
     */
    public void registerCommand(ICommand cmd){
        if (saveEntities)
            commands.add(cmd);
    }

    /**
     * Get all the commands registered by this plugin. this will not work if {@link Setting#DISABLE_ENTITY_REFERENCE} is used
     *
     * @return {@link ArrayList} containing all the commands
     */
    public List<Command> getCommands() {
        return commands;
    }

    /**
     * Get all the listeners registered by this plugin. this will not work if {@link Setting#DISABLE_ENTITY_REFERENCE} is used
     *
     * @return {@link ArrayList} containing all the listeners
     */
    public List<Listener> getListeners() {
        return listeners;
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
     * Get the plugin's terminable registry
     *
     * @return The terminable registry.
     *
     * @see CompositeTerminable
     */
    public CompositeTerminable getTerminableRegistry() {
        return terminableRegistry;
    }

    /**
     * Reload the config {@link JavaPlugin#reloadConfig()} and the {@link ConfigCache} if its being used.
     */
    @Override
    public void reloadConfig() {
        if (UtilsMain.isUsingConfigCache())
            ConfigCache.getInstance().clear();

        super.reloadConfig();
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
     * Check if a plugin exists and is enabled on the server
     *
     * @param name The name of the plugin
     * @return Whether it exists
     */
    public boolean isPluginPresent(String name) {
        return getServer().getPluginManager().getPlugin(name) != null;
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
