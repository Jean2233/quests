package com.github.jean2233.quests.storage;

import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.Optional;

public interface IStorage<T> {
    void createTable();

    void insert(T t);
    void update(T t);

    T find(String id);
    T adapt(ResultSet set);
}