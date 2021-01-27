package me.border.spigotutilities;

import me.border.spigotutilities.baseutils.Utils;
import me.border.spigotutilities.mojang.listener.PlayerJoinHandler;

/**
 * Adapter class to avoid including {@link me.border.spigotutilities.mojang.PlayerInfo} and all the classes that it uses in a shaded jar
 */
public class PlayerInfoAdapter {

    public static void init(){
        Utils.registerListener(new PlayerJoinHandler());
    }
}
