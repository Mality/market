package com.example.payroll.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Map;

@Entity
public class Product {

    private @Id @GeneratedValue Long id;

    private String title;
    private String description;

    private Integer cost;
}
