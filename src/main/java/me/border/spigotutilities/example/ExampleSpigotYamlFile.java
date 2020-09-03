package me.border.spigotutilities.example;

import me.border.spigotutilities.file.AbstractSpigotYamlFile;

import java.io.File;

public class ExampleSpigotYamlFile extends AbstractSpigotYamlFile {

    protected ExampleSpigotYamlFile() {
        super("example", new File("/data"));
    }
}
