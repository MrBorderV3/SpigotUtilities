package me.border.spigotutilities.example;

import me.border.spigotutilities.file.AbstractYamlFile;

import java.io.File;

public class ExampleYamlFile extends AbstractYamlFile {

    public ExampleYamlFile() {
        super("example", new File("/data"));
    }
}
