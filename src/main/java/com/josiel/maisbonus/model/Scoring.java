package com.josiel.maisbonus.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Scoring {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    private String description;

    private double purchaseAmount;

    private double cashbackAmount;

    private LocalDateTime timestamp;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Company company;

}
