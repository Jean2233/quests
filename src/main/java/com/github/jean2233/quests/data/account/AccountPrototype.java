package com.github.jean2233.quests.data.account;

import com.github.jean2233.quests.data.quest.Quest;
import com.github.jean2233.quests.data.quest.QuestData;
import com.github.jean2233.quests.data.quest.QuestRegistry;
import com.github.jean2233.quests.data.quest.QuestType;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public record AccountPrototype(QuestRegistry registry) {

    public void progressQuest(Account account, QuestType type) {
        final String questId = account.getQuestId();
        if(questId == null) return;

        final Quest quest = registry.getById(questId);
        if(quest == null) return;

        final QuestData data = quest.getData();
        if(data.getType() != type) return;

        account.incrementProgress(1);

        if(account.getProgress() >= data.getRequirement()) finishQuest(account, quest);
    }

    private void finishQuest(Account account, Quest quest) {
        final Player player = Bukkit.getPlayer(account.getId());
        if(player == null) return;

        final QuestData data = quest.getData();
        data.getCommands().forEach(command -> {
            Bukkit.dispatchCommand(
              Bukkit.getConsoleSender(), command
                .replace("@player", player.getName())
                .replace("@questId", quest.getId())
            );
        });

        player.sendMessage("§aQuest completed successfully!");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);

        account.setQuestId("null");
        account.setProgress(0);

        account.getFinishedQuests().add(quest.getId());
    }
}