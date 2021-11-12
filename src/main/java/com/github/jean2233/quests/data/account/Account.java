package com.github.jean2233.quests.data.account;

import com.github.jean2233.quests.data.quest.Quest;
import com.github.jean2233.quests.data.quest.QuestData;
import com.github.jean2233.quests.data.quest.QuestType;
import lombok.Builder;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Account {

    private final UUID id;

    private Quest quest;
    private int progress = 0;

    private final List<Quest> finishedQuests = new ArrayList<>();

    public boolean isFinished(Quest quest) {
        return finishedQuests.contains(quest);
    }

    public void execute(QuestType questType, int value) {
        if(quest == null) return;

        final QuestData data = quest.getData();
        if(data.getType() != questType) return;

        this.progress += value;

        if(progress >= quest.getData().getRequirement()) finishQuest(quest);
    }

    private void finishQuest(Quest quest) {
        final Player player = Bukkit.getPlayer(id);
        assert player != null;

        player.sendMessage("§aQuest completed successfully!");
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1F, 1F);

        setQuest(null);
        setProgress(0);

        finishedQuests.add(quest);
    }
}