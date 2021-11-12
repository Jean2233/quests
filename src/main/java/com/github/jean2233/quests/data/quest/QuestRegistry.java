package com.github.jean2233.quests.data.quest;

import com.github.jean2233.quests.loader.impl.QuestDataLoader;
import com.github.jean2233.quests.loader.impl.QuestLoader;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class QuestRegistry {

    @Getter
    private final List<Quest> quests = new ArrayList<>();

    public void registerQuests(FileConfiguration configuration) {
        final QuestLoader questLoader = new QuestLoader(new QuestDataLoader());

        final ConfigurationSection mainSection = configuration.getConfigurationSection("quests");
        if(mainSection == null) return;

        System.out.println(mainSection);

        for (String key : mainSection.getKeys(false)) {
            final ConfigurationSection questSection = mainSection.getConfigurationSection(key);
            System.out.println(questSection);

            final Quest quest = questLoader.load(key, questSection);
            if(quest == null) continue;

            System.out.println(quest.toString());

            registerQuest(quest);
        }
    }

    public Quest getById(String id) {
        return quests.stream().filter(
          quest -> quest.getId().equalsIgnoreCase(id)
        ).findFirst().orElse(null);
    }

    private void registerQuest(Quest quest) {
        quests.add(quest);
    }
}