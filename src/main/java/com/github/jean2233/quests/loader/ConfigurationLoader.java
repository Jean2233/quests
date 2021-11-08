package com.github.jean2233.quests.loader;

import org.bukkit.configuration.ConfigurationSection;

public interface ConfigurationLoader<V> {
    V load(String key, ConfigurationSection section);
}