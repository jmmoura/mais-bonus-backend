package com.josiel.maisbonus.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@Entity
public class Company {

    @Id
    private String cnpj;

    @ManyToMany
    private Set<Customer> customers;

    @OneToMany(mappedBy = "company")
    private List<Scoring> scoringList;

    @OneToOne(mappedBy = "company")
    private User user;

}
