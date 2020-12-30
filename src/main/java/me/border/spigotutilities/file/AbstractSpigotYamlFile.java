package me.border.spigotutilities.file;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractSpigotYamlFile {

    private static final Set<AbstractSpigotYamlFile> files = new HashSet<>();

    private final File path;
    private final File file;
    private FileConfiguration data;

    public AbstractSpigotYamlFile(String file, File path){
        files.add(this);
        this.path = path;
        this.file = new File(path, file + ".yml");
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


    public void save() throws Exception {
        this.data.save(this.file);
    }

    public void saveSilently(){
        try {
            save();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    public void reload(){
        this.data = YamlConfiguration.loadConfiguration(file);
    }

    public File getPath(){
        return path;
    }

    public File getFile() {
        return file;
    }

    public static void setupAll(){
        files.forEach(AbstractSpigotYamlFile::setup);
    }

    public static void saveAll(){
        files.forEach(AbstractSpigotYamlFile::saveSilently);
    }
}