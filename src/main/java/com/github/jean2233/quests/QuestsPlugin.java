package com.github.jean2233.quests;

import com.github.jean2233.quests.command.QuestCommand;
import com.github.jean2233.quests.data.account.*;
import com.github.jean2233.quests.data.quest.QuestRegistry;
import com.github.jean2233.quests.listener.ConnectionListener;
import com.github.jean2233.quests.listener.QuestListener;
import com.github.jean2233.quests.view.QuestView;
import lombok.Getter;
import lombok.SneakyThrows;
import me.saiintbrisson.minecraft.ViewFrame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.DriverManager;
import java.util.UUID;

@Getter
public final class QuestsPlugin extends JavaPlugin {

    private ViewFrame viewFrame;

    private AccountRegistry accountRegistry;
    private AccountStorage accountStorage;
    private AccountFactory accountFactory;
    private AccountPrototype accountPrototype;

    private QuestRegistry questRegistry;

    @SneakyThrows
    @Override
    public void onLoad() {
        saveDefaultConfig();

        this.accountRegistry = new AccountRegistry();
        this.questRegistry = new QuestRegistry();
    }

    @SneakyThrows
    @Override
    public void onEnable() {
        this.accountStorage = new AccountStorage(DriverManager.getConnection(
          getConfig().getString("mysql.connection-url"),
          getConfig().getString("mysql.username"),
          getConfig().getString("mysql.password")
        ));

        this.accountFactory = new AccountFactory(accountRegistry, accountStorage);
        this.accountPrototype = new AccountPrototype(questRegistry);

        createDefaultRegistry();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            final UUID uniqueId = player.getUniqueId();

            final Account account = accountRegistry.getById(uniqueId);
            accountStorage.update(account);
        });
    }

    private void createDefaultRegistry() {
        questRegistry.registerQuests(getConfig());

        this.viewFrame = new ViewFrame(this);
        viewFrame.register(new QuestView(questRegistry.getQuests()));

        final PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new QuestListener(accountRegistry, accountPrototype), this);
        pluginManager.registerEvents(new ConnectionListener(accountFactory), this);

        getCommand("quests").setExecutor(new QuestCommand(accountRegistry, viewFrame));
    }
}