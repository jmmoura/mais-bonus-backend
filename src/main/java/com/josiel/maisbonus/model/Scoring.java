package com.josiel.maisbonus.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
