package com.github.jean2233.quests.data.quest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Quest {

    private final String id;

    private final QuestData data;
}