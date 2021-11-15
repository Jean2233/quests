package com.github.jean2233.quests.data.account;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public record AccountFactory(AccountRegistry registry, AccountStorage storage) {

    public void create(Player player) {
        final UUID uniqueId = player.getUniqueId();

        final Account find = storage.find(uniqueId.toString());
        if(find != null) {
            registry.register(find);
            return;
        }

        final Account account = Account.builder()
          .id(player.getUniqueId())
          .questId("null")
          .finishedQuests(new ArrayList<>())
          .build();

        registry.register(account);
        storage.insert(account);
    }

    public void remove(Player player) {
        final UUID playerId = player.getUniqueId();

        final Account account = registry.getById(playerId);
        if(account == null) return;

        registry.unregister(account);
        storage.update(account);
    }
}