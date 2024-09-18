package ru.pas_zhukov.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Account {
    private int id;
    private int userId;
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
}
