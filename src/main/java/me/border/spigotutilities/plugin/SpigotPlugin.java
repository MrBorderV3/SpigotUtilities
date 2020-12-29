package me.border.spigotutilities.plugin;

import me.border.spigotutilities.UtilsMain;
import me.border.spigotutilities.file.AbstractSpigotYamlFile;
import me.border.utilities.file.AbstractSerializedFile;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EnumSet;

public abstract class SpigotPlugin extends JavaPlugin {
    private static SpigotPlugin instance;
    
    private EnumSet<Setting> settings;

    public SpigotPlugin(){
        super();
    }

    public SpigotPlugin(EnumSet<Setting> settings){
        super();
        this.settings = settings;
    }

    protected void load() {}
    protected void enable() {}
    protected void disable() {}

    @Override
    public void onEnable() {
        instance = this;
        UtilsMain.init(this);
        if (settings.contains(Setting.SETUP_RESOURCES)){
            AbstractSpigotYamlFile.setupAll();
            AbstractSerializedFile.setupAll();
        }
    }


    @Override
    public void onDisable() {
        if (settings.contains(Setting.SAVE_RESOURCES)) {
            AbstractSpigotYamlFile.saveAll();
            AbstractSerializedFile.saveAll();
        }
    }

    private void registerListener(Listener listener){
        getServer().getPluginManager().registerEvents(listener, this);
    }

    private void registerCommand(CommandExecutor executor, String cmd){
        getCommand(cmd).setExecutor(executor);
    }

    public EnumSet<Setting> getSettings() {
        return settings;
    }

    public static SpigotPlugin getInstance() {
        return instance;
    }
}
