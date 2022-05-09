package com.example.payroll.core.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    private @Id @GeneratedValue Long id;

    private String name;
    private String role;

    private Long balance;

//    @Transient
    private Long processedBalance = 0L;

    public User() {}

    public User(String name, String role, Long balance) {
        this.name = name;
        this.role = role;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public Long getBalance() {
        return balance;
    }

    public Long getProcessedBalance() {
        return processedBalance;
    }

    @JsonIgnore
    public Long getFreeBalance() {
        return balance - processedBalance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void setProcessedBalance(Long processedBalance) {
        this.processedBalance = processedBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(role, user.role) && Objects.equals(balance, user.balance) && Objects.equals(processedBalance, user.processedBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, role, balance, processedBalance);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", balance=" + balance +
                ", processedBalance=" + processedBalance +
                '}';
    }
}
