package com.demo.user.management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "portfolio")
@Entity
@Data
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Double valuation;

    private String currency;

    public Portfolio() {
    }

    public Portfolio(String description, Double valuation, String currency) {
        this.description = description;
        this.valuation = valuation;
        this.currency = currency;
    }
}
