package me.border.spigotutilities;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public abstract class AbstractYamlFile {

    protected Plugin plugin = UtilsMain.getInstance();
    protected File path;
    protected File file;
    protected FileConfiguration data;

    protected AbstractYamlFile(String name){
        this.path = new File(plugin.getDataFolder() + File.separator + "/data");
        this.file = new File(path, name + ".yml");
    }

    public void setup(){
        if (!path.exists()){
            path.mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.data = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getData(){
        return this.data;
    }

    public void save(){
        try {
            this.data.save(this.file);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void reload(){
        this.data = YamlConfiguration.loadConfiguration(file);
    }
}