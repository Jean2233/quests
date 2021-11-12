package com.github.jean2233.quests.data.quest;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Quest {

    private final String id;

    private final QuestData data;
}