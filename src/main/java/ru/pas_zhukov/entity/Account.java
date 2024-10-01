package ru.pas_zhukov.entity;


public class Account {
    private final int id;
    private final int userId;
    private Long moneyAmount;

    public Account(int id, int userId, Long moneyAmount) {
        this.id = id;
        this.userId = userId;
        this.moneyAmount = moneyAmount;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Long getMoneyAmount() {
        return moneyAmount;
    }

    public void depositMoney(Long moneyAmount) {
        this.moneyAmount += moneyAmount;
    }

    public void withdrawMoney(Long moneyAmount) {
        this.moneyAmount -= moneyAmount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", userId=" + userId +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
