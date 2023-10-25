package com.josiel.maisbonus.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    private String cpf;

    @ManyToMany(mappedBy = "customers")
    private Set<Company> companies;

    @OneToMany(mappedBy = "customer")
    private List<Scoring> scoringList;

    @OneToOne(mappedBy = "customer")
    private User user;

}
