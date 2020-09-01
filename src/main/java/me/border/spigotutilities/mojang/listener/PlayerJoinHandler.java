package me.border.spigotutilities.mojang.listener;

import me.border.spigotutilities.mojang.api.MojangCacheManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinHandler implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        MojangCacheManager.updateCache(e.getPlayer().getUniqueId(), e.getPlayer().getName());
    }
}
