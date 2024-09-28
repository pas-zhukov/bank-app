package ru.pas_zhukov.entity;

import ru.pas_zhukov.exception.request.AccountNotFoundException;

import java.util.ArrayList;
import java.util.List;


public class User {
    private final int id;
    private final String login;
    private List<Account> accountList = new ArrayList<>();


    public User(int id, String login) {
        this.id = id;
        this.login = login;
    }

    public User(int id, String login, List<Account> accountList) {
        this.id = id;
        this.login = login;
        this.accountList = accountList;
    }

    public void addAccount(Account account) {
        accountList.add(account);
    }

    public void removeAccount(Account account) {
        accountList.remove(account);
    }

    public void removeAccount(Integer accountId) {
        accountList.removeIf(account -> accountId.equals(account.getId()));
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", accountList=" + accountList +
                '}';
    }
}
