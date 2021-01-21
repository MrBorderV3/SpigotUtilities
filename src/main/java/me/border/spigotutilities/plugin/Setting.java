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
    PLAYER_INFO
}
