package com.josiel.maisbonus.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    private String cnpj;

    private String name;

    private String email;

    private String phone;

    @ManyToMany
    private Set<Customer> customers;

    @OneToMany(mappedBy = "company")
    private List<Scoring> scoringList;

    @OneToOne(cascade=CascadeType.ALL)
    private User user;

}
