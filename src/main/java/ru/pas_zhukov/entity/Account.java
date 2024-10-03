package ru.pas_zhukov.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "money_amount")
    private Long moneyAmount;

    public Account() {

    }

    public Account(User user, Long moneyAmount) {
        this.user = user;
        this.moneyAmount = moneyAmount;
    }

    public Account(Integer id, User user, Long moneyAmount) {
        this.id = id;
        this.user = user;
        this.moneyAmount = moneyAmount;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Integer getUserId() {
        return user.getId();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Long moneyAmount) {
        this.moneyAmount = moneyAmount;
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
                ", userId=" + user.getId() +
                ", moneyAmount=" + moneyAmount +
                '}';
    }
}
