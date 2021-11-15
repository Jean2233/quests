package com.github.jean2233.quests.data.account;

import com.github.jean2233.quests.data.quest.Quest;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Account {

    private final UUID id;

    private String questId;
    private int progress;

    private final List<String> finishedQuests;

    public void incrementProgress(int value) {
        this.progress += value;
    }

    public boolean isFinished(Quest quest) {
        return finishedQuests.contains(quest.getId());
    }
}