package com.github.jean2233.quests.data.account;

import com.github.jean2233.quests.storage.IStorage;
import com.github.jean2233.quests.storage.util.SQLReader;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AccountStorage implements IStorage<Account> {

    private final Connection connection;
    private final SQLReader reader;

    @SneakyThrows
    public AccountStorage(Connection connection) {
        this.connection = connection;

        this.reader = new SQLReader();
        reader.loadFromResources();
    }

    @SneakyThrows
    @Override
    public void createTable() {
        final PreparedStatement statement = prepareStatement("create_table");
        statement.executeUpdate();
    }

    @SneakyThrows
    @Override
    public void insert(Account account) {
        final PreparedStatement statement = prepareStatement("insert");
        statement.setString(1, account.getId().toString());
        statement.setString(2, account.getQuestId());
        statement.setInt(3, account.getProgress());
        statement.setString(4, String.join(", ", account.getFinishedQuests()));

        statement.executeUpdate();
    }

    @SneakyThrows
    @Override
    public void update(Account account) {
        final PreparedStatement statement = prepareStatement("update");
        statement.setString(1, account.getQuestId());
        statement.setInt(2, account.getProgress());
        statement.setString(3, String.join(", ", account.getFinishedQuests()));

        statement.executeUpdate();
    }

    @SneakyThrows
    @Override
    public Account find(String id) {
        final PreparedStatement statement = prepareStatement("find");
        statement.setString(1, id);

        final ResultSet set = statement.executeQuery();
        if(!set.next()) return null;

        return adapt(set);
    }

    @SneakyThrows
    @Override
    public Account adapt(ResultSet set) {
        final UUID id = UUID.fromString(set.getString("id"));

        final String questId = set.getString("quest");
        final int progress = set.getInt("progress");

        final List<String> finishedQuests = Arrays.stream(set.getString("finished_quests")
          .split(", "))
          .map(String::toString)
          .collect(Collectors.toList());

        return Account.builder()
          .id(id)
          .questId(questId)
          .progress(progress)
          .finishedQuests(finishedQuests)
          .build();
    }

    @SneakyThrows
    private PreparedStatement prepareStatement(String sqlFile) {
        return connection.prepareStatement(reader.getSql(sqlFile));
    }
}