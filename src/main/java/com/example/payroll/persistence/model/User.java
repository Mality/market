package com.example.payroll.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user_account")
public class User {

    private @Id @GeneratedValue Long id;

    private String name;
    private String email;
    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id")}
    )
    private Set<Role> roles = new HashSet<>();

    private Long balance;

    private Long processedBalance = 0L;

    public User() {}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = 0L;
    }

    public User(String name, String email, String password, Long balance) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.balance = balance;
    }

    public User(String name, String email, String password, Set<Role> roles, Long balance) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Role> getRoles() {
        return roles;
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

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(roles, user.roles) && Objects.equals(balance, user.balance) && Objects.equals(processedBalance, user.processedBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, roles, balance, processedBalance);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", balance=" + balance +
                ", processedBalance=" + processedBalance +
                '}';
    }
}
