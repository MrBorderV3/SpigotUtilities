package me.border.spigotutilities.example;

import me.border.spigotutilities.file.AbstractSerializedFile;

import java.io.File;
import java.util.HashMap;

public class ExampleSerializedFile extends AbstractSerializedFile<HashMap<String, String>> {

    public ExampleSerializedFile() {
        super("example", new File("/data"), new HashMap<>());
    }
}
