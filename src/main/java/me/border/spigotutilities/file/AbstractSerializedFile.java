package me.border.spigotutilities.file;

import java.io.*;

public abstract class AbstractSerializedFile<I> {

    private File file;
    private File path;
    public I item;

    public AbstractSerializedFile(String file, File path, I item) {
        this.path = path;
        this.file = new File(path, file + ".dat");
        this.item = item;
    }

    public void setup() {
        if (!this.path.exists()){
            this.path.mkdirs();
        }
        if (this.file.exists()) {
            this.item = load();
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void save() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(item);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public I load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            Object result = ois.readObject();
            ois.close();
            return (I) result;
        } catch (Exception e) {
            return null;
        }
    }


    public File getPath(){
        return path;
    }

    public File getFile() {
        return file;
    }

    public I getItem(){
        return item;
    }
}
