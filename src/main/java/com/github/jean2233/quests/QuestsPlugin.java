package com.github.jean2233.quests;

import com.github.jean2233.quests.command.QuestCommand;
import com.github.jean2233.quests.data.account.AccountFactory;
import com.github.jean2233.quests.data.account.AccountRegistry;
import com.github.jean2233.quests.data.quest.QuestRegistry;
import com.github.jean2233.quests.listener.ConnectionListener;
import com.github.jean2233.quests.listener.QuestListener;
import com.github.jean2233.quests.view.QuestView;
import lombok.Getter;
import me.saiintbrisson.minecraft.ViewFrame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class QuestsPlugin extends JavaPlugin {

    private ViewFrame viewFrame;

    private AccountRegistry accountRegistry;
    private AccountFactory accountFactory;

    private QuestRegistry questRegistry;

    @Override
    public void onLoad() {
        saveDefaultConfig();

        this.accountRegistry = new AccountRegistry();
        this.accountFactory = new AccountFactory();

        this.questRegistry = new QuestRegistry();
    }

    @Override
    public void onEnable() {
        createDefaultRegistry();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> {});
    }

    private void createDefaultRegistry() {
        questRegistry.registerQuests(getConfig());

        this.viewFrame = new ViewFrame(this);
        viewFrame.register(new QuestView(questRegistry.getQuests()));

        final PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new QuestListener(accountFactory), this);
        pluginManager.registerEvents(new ConnectionListener(accountFactory), this);

        getCommand("quests").setExecutor(new QuestCommand(accountFactory, viewFrame));
    }
}