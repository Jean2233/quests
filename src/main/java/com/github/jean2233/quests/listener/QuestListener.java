package com.github.jean2233.quests.listener;

import com.github.jean2233.quests.data.account.Account;
import com.github.jean2233.quests.data.account.AccountFactory;
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

public record QuestListener(AccountFactory accountFactory) implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        this.execute(event.getPlayer(), QuestType.BREAK, 1);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        this.execute(event.getPlayer(), QuestType.PLACE, 1);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        final LivingEntity entity = event.getEntity();

        final Player player = entity.getKiller();
        if(player == null) return;

        this.execute(player, QuestType.KILL_MOBS, 1);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        final Location from = event.getFrom();
        final Location to = event.getTo();
        
        if (to.getBlockX() == from.getBlockX()
          && to.getBlockY() == to.getBlockY()
          && to.getBlockZ() == from.getBlockZ()
        ) return;

        this.execute(event.getPlayer(), QuestType.WALK, 1);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        this.execute(event.getPlayer(), QuestType.COMMAND, 1);
    }

    private void execute(Player player, QuestType questType, int amount) {
        final UUID playerId = player.getUniqueId();

        final Account account = accountFactory.getById(playerId);
        if(account == null) return;

        account.execute(questType, amount);
    }
}