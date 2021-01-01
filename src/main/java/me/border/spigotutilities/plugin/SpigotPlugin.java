package me.border.spigotutilities.plugin;

import me.border.spigotutilities.UtilsMain;
import me.border.spigotutilities.file.AbstractSpigotYamlFile;
import me.border.spigotutilities.task.Schedulers;
import me.border.utilities.file.AbstractSerializedFile;
import me.border.utilities.terminable.Terminable;
import me.border.utilities.terminable.composite.CompositeTerminable;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.EnumSet;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
        UtilsMain.init(instance);
        Schedulers.newBuilder()
                .async()
                .after(30, TimeUnit.SECONDS)
                .every(30, TimeUnit.SECONDS)
                .run(terminableRegistry::cleanup)
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
        this.terminableRegistry.closeSilently();
        disable();
        if (settings.contains(Setting.SAVE_RESOURCES)) {
            AbstractSpigotYamlFile.saveAll();
            AbstractSerializedFile.saveAll();
        }
    }

    public <T extends Terminable> T bind(T terminable) {
        return this.terminableRegistry.bind(terminable);
    }

    private void registerListener(Listener listener){
        Objects.requireNonNull(listener, "listener");
        getServer().getPluginManager().registerEvents(listener, this);
    }

    private void registerCommand(CommandExecutor executor, String cmd){
        getCommand(cmd).setExecutor(executor);
    }

    public boolean isPluginPresent(String name) {
        return getServer().getPluginManager().getPlugin(name) != null;
    }

    public File getRelativeFile(String name) {
        getDataFolder().mkdirs();
        return new File(getDataFolder(), name);
    }

    public EnumSet<Setting> getSettings() {
        return settings;
    }

    public static SpigotPlugin getInstance() {
        return instance;
    }
}
