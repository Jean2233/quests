package com.github.jean2233.quests;

import com.github.jean2233.quests.data.account.AccountRegistry;
import com.github.jean2233.quests.data.quest.QuestRegistry;
import com.github.jean2233.quests.loader.impl.QuestLoader;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class QuestsPlugin extends JavaPlugin {

    private AccountRegistry accountRegistry;
    private QuestRegistry questRegistry;

    @Override
    public void onLoad() {
        saveDefaultConfig();

        this.accountRegistry = new AccountRegistry();
        this.questRegistry = new QuestRegistry();
    }

    @Override
    public void onEnable() {
        questRegistry.registerQuests(getConfig());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
