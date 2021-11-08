package com.github.jean2233.quests.data.quest;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class QuestData {

    private final QuestType type;

    private final int requirement;

    private final List<String> commands;
}