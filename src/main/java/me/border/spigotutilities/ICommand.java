package me.border.spigotutilities;

import com.sun.istack.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

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

    public ICommand(String name, boolean player, @Nullable String permission) {
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
            String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            Class craftServerNMS = Class.forName("org.bukkit.craftbukkit." + version + ".CraftServer");
            Object pluginServer = craftServerNMS.cast(Bukkit.getServer());
            Object commandMap = pluginServer.getClass().getMethod("getCommandMap").invoke(pluginServer);
            commandMap.getClass().getMethod("register", String.class, Command.class).invoke(commandMap, plugin.getDescription().getName(), this);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean execute(CommandSender arg0, String arg1, String[] arg2) {
        if (this.getPermission() != null) {
            if (!this.getPermission().equals("")) {
                if (!CommandUtils.permCheck(arg0, this.getPermission()))
                    return false;
            }
        }

        if (player && !CommandUtils.playerCheck(arg0)) return false;

        return commandUsed(arg0, arg2);
    }

    public abstract boolean commandUsed(CommandSender sender, String[] args);
}
