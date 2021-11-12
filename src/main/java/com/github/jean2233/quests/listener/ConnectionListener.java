package com.github.jean2233.quests.listener;

import com.github.jean2233.quests.data.account.AccountFactory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public record ConnectionListener(AccountFactory factory) implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        factory.create(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        factory.remove(event.getPlayer());
    }
}