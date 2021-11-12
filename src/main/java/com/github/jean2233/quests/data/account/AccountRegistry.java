package com.github.jean2233.quests.data.account;

import lombok.Getter;

import java.util.*;

public class AccountRegistry {

    @Getter
    private final List<Account> accounts = new ArrayList<>();

    public void register(Account account) {
        accounts.add(account);
    }

    public void unregister(Account account) {
        accounts.remove(account);
    }

    public Account getById(UUID id) {
        return accounts.stream().filter(
          account -> account.getId().equals(id)
        ).findFirst().orElse(null);
    }
}