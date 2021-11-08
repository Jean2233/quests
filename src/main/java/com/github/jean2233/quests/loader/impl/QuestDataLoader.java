package com.github.jean2233.quests.loader.impl;

import com.github.jean2233.quests.data.quest.QuestData;
import com.github.jean2233.quests.data.quest.QuestType;
import com.github.jean2233.quests.loader.ConfigurationLoader;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class QuestDataLoader implements ConfigurationLoader<QuestData> {
    @Override
    public QuestData load(String key, ConfigurationSection section) {
        final QuestType questType = QuestType.valueOf(section.getString("quest-type"));

        final int requirement = section.getInt("requirement");
        final List<String> commands = section.getStringList("commands");

        return QuestData.builder()
          .type(questType)
          .requirement(requirement)
          .commands(commands)
          .build();
    }
}