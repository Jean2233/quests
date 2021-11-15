package com.github.jean2233.quests.listener;

import com.github.jean2233.quests.data.account.Account;
import com.github.jean2233.quests.data.account.AccountPrototype;
import com.github.jean2233.quests.data.account.AccountRegistry;
import com.github.jean2233.quests.data.quest.QuestType;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

public record QuestListener(AccountRegistry registry, AccountPrototype prototype) implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        this.execute(event.getPlayer(), QuestType.BREAK);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        this.execute(event.getPlayer(), QuestType.PLACE);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        final LivingEntity entity = event.getEntity();

        final Player player = entity.getKiller();
        if(player == null) return;

        this.execute(player, QuestType.KILL_MOBS);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        final Location from = event.getFrom();
        final Location to = event.getTo();
        
        if (to.getBlockX() == from.getBlockX()
          && to.getBlockY() == to.getBlockY()
          && to.getBlockZ() == from.getBlockZ()
        ) return;

        this.execute(event.getPlayer(), QuestType.WALK);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        this.execute(event.getPlayer(), QuestType.COMMAND);
    }

    private void execute(Player player, QuestType questType) {
        final UUID playerId = player.getUniqueId();

        final Account account = registry.getById(playerId);
        if(account == null) return;

        prototype.progressQuest(account, questType);
    }
}