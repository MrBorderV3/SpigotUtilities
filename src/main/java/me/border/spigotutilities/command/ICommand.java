package me.border.spigotutilities.command;

import me.border.spigotutilities.baseutils.ChatUtils;
import me.border.spigotutilities.baseutils.Utils;
import me.border.spigotutilities.plugin.UtilsMain;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static me.border.spigotutilities.baseutils.CommandUtils.*;

public abstract class ICommand extends Command {
    protected String cmd_args;
    private JavaPlugin plugin = UtilsMain.getInstance();
    private boolean player;

    public ICommand(String name, String description, String permission, boolean player, String... aliases) {
        super(name);
        this.player = player;
        this.usageMessage = Utils.ucs("illegalArguments");
        this.description = description;
        this.setAliases(Arrays.asList(aliases));
        this.setPermission(permission);

        registerWithBukkit();
    }

    public ICommand(String name, boolean player, String permission) {
        super(name);
        this.player = player;
        this.setPermission(permission);

        registerWithBukkit();
    }

    public ICommand(String name, String description, boolean player, String permission) {
        super(name);
        this.player = player;
        this.setPermission(permission);
        this.description = description;

        registerWithBukkit();
    }

    public ICommand(String name, String ro_arguments, String description, String permission, boolean player, String... aliases) {
        super(name);
        this.player = player;
        this.usageMessage = ChatUtils.colorize("&eCommand Usage: &6/" + name + " &7" + ro_arguments);
        this.description = description;
        this.cmd_args = ro_arguments;
        this.setAliases(Arrays.asList(aliases));
        this.setPermission(permission);

        registerWithBukkit();
    }

    public ICommand(String name, String permission, boolean player, String... aliases) {
        super(name);
        this.player = player;
        this.setAliases(Arrays.asList(aliases));
        this.setPermission(permission);

        registerWithBukkit();
    }

    private void registerWithBukkit() {
        try {
            Class craftServerNMS;
            // For some reason in version 1.21 they decided to remove the version from the package (fucking a-holes).
            // Note: if they change it back in 1.22 im ending support for the plugin <and my life)
            // I cant take the versional changes no more
            int subVersion = Integer.parseInt(Bukkit.getBukkitVersion().split("\\.")[1].split("-")[0]);

            if (subVersion <= 20) {
                String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
                craftServerNMS = Class.forName("org.bukkit.craftbukkit." + version + ".CraftServer");
            } else {
                craftServerNMS = Class.forName("org.bukkit.craftbukkit.CraftServer");
            }

            Object pluginServer = craftServerNMS.cast(Bukkit.getServer());
            Object commandMap = pluginServer.getClass().getMethod("getCommandMap").invoke(pluginServer);
            commandMap.getClass().getMethod("register", String.class, Command.class).invoke(commandMap, plugin.getDescription().getName(), this);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(CommandSender arg0, String arg1, String[] arg2) {
        if (this.getPermission() != null && !this.getPermission().equals("")) {
            if (!permCheck(arg0, this.getPermission()))
                return false;
        }

        if (player && !playerCheck(arg0)) return false;

        return commandUsed(arg0, arg2);
    }

    public abstract boolean commandUsed(CommandSender sender, String[] args);
}
