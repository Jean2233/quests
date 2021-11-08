package com.github.jean2233.quests.data.account;

import com.github.jean2233.quests.data.quest.Quest;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class Account {

    private final UUID id;

    private final Map<Quest, Integer> progressMap;

    public void updateProgress(Quest quest, int value) {
        final int currentValue = progressMap.getOrDefault(quest, 0);
        final int updatedValue = (currentValue + value);

        progressMap.put(quest, updatedValue);
    }
}