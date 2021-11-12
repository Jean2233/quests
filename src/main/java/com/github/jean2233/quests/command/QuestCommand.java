package com.github.jean2233.quests.command;

import com.github.jean2233.quests.data.account.Account;
import com.github.jean2233.quests.data.account.AccountFactory;
import com.github.jean2233.quests.data.account.AccountRegistry;
import com.github.jean2233.quests.view.QuestView;
import com.google.common.collect.ImmutableMap;
import me.saiintbrisson.minecraft.ViewFrame;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public record QuestCommand(AccountFactory factory, ViewFrame viewFrame) implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return false;

        final Player player = (Player) sender;
        final UUID playerId = player.getUniqueId();

        final Account account = factory.getById(playerId);
        if(account == null) return false;

        viewFrame.open(QuestView.class, player, ImmutableMap.of("account", account));
        player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 1F, 1F);

        return false;
    }
}