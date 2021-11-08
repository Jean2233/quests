package com.github.jean2233.quests.loader.impl;

import com.github.jean2233.quests.data.quest.Quest;
import com.github.jean2233.quests.data.quest.QuestData;
import com.github.jean2233.quests.loader.ConfigurationLoader;
import org.bukkit.configuration.ConfigurationSection;

public record QuestLoader(QuestDataLoader loader) implements ConfigurationLoader<Quest> {
    @Override
    public Quest load(String key, ConfigurationSection section) {
        final QuestData data = loader.load(key, section);

        return Quest.builder()
          .id(key)
          .data(data)
          .build();
    }
}