package com.github.jean2233.quests.view;

import com.github.jean2233.quests.data.account.Account;
import com.github.jean2233.quests.data.quest.Quest;
import com.github.jean2233.quests.data.quest.QuestData;
import me.saiintbrisson.minecraft.*;
import me.saiintbrisson.minecraft.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class QuestView extends PaginatedView<Quest> {

    public QuestView(List<Quest> questList) {
        super(6, "Quests");

        setCancelOnClick(true);

        setSource(questList);
        setLayout(
          "XXXXXXXXX",
          "XOOOOOOOX",
          "XOOOOOOOX",
          "XOOOOOOOX",
          "XOOOOOOOX",
          "XXXXXXXXX"
        );
    }

    @Override
    protected void onPaginationItemRender(PaginatedViewContext<Quest> context, ViewItem viewItem, Quest quest) {
        viewItem.onRender(slot -> {
            final Account account = slot.get("account");
            if(account == null) return;

            slot.setItem(questItem(account, quest));
        }).onClick(questHandler(quest));
    }

    private ViewItemHandler questHandler(Quest quest) {
        return context -> {
            final Player player = context.getPlayer();

            final Account account = context.get("account");
            if(account.isFinished(quest)) return;

            final String currentQuest = account.getQuestId();
            if (currentQuest != null && currentQuest.equals(quest.getId())) return;

            account.setQuestId(quest.getId());
            account.setProgress(0);

            player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1F, 1F);

            context.close();
        };
    }

    private ItemStack questItem(Account account, Quest quest) {
        final QuestData data = quest.getData();

        final ItemBuilder builder = new ItemBuilder(Material.WRITABLE_BOOK);
        builder.name("§aQuest " + quest.getId());
        builder.lore(
          "§fType: §7" + data.getType(),
          "§fRequirement: §7" + data.getRequirement(),
          ""
        );

        if(account.isFinished(quest)) {
            builder.addLoreLine("§cFinished");
        } else {
            final String currentQuest = account.getQuestId();
            if (currentQuest == null || !currentQuest.equals(quest.getId())) {
                builder.addLoreLine("§aClick to start");
            } else {
                builder.addLoreLine("§fProgress: §7" + account.getProgress() + "/" + data.getRequirement());
            }
        }

        return builder.build();
    }
}