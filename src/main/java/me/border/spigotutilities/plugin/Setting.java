package me.border.spigotutilities.plugin;

import me.border.spigotutilities.mojang.PlayerInfo;

public enum Setting {
    /**
     * Automatic saving of resources at {@link SpigotPlugin#onDisable()}
     */
    SAVE_RESOURCES,

    /**
     * Automatic setup of resources at {@link SpigotPlugin#onEnable()}
      */
    SETUP_RESOURCES,

    /**
     * Automatic initialization of {@link PlayerInfo} class.
     */
    PLAYER_INFO,

    /**
     * Disable saving references of {@link org.bukkit.command.Command} and {@link org.bukkit.event.Listener} entities.
     */
    DISABLE_ENTITY_REFERENCE,

    /**
     * Disable caching of variables obtains from the config.
     */
    DISABLE_CONFIG_CACHE
}
