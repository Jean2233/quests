package com.github.jean2233.quests.data.account;

import org.bukkit.entity.Player;

import java.util.UUID;

public class AccountFactory extends AccountRegistry {

    public void create(Player player) {
        final UUID playerId = player.getUniqueId();

        Account account = getById(playerId); // switch to database
        if(account == null) {
            account = Account.builder()
              .id(playerId)
              .build();

            // TODO: database insert
        }

        register(account);
    }

    public void remove(Player player) {
        final UUID playerId = player.getUniqueId();

        final Account account = getById(playerId);
        if(account == null) return;

        unregister(account);
    }
}