package com.example.payroll.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_order")
public class Order {

    private @Id @GeneratedValue Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    private String description;
    private Status status;

    private Long cost;

    public Order() {}

    public Order(User user, String description, Status status, Long cost) {
        this.user = user;
        this.description = description;
        this.status = status;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public Long getCost() {
        return cost;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(user, order.user) && Objects.equals(description, order.description) && status == order.status && Objects.equals(cost, order.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, description, status, cost);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", cost=" + cost +
                '}';
    }
}
