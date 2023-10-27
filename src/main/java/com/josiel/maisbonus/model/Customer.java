package com.josiel.maisbonus.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long Id;

    private String cpf;

    private String name;

    private String email;

    private String phone;

    private String customerID;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "company_customer",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Company> companies;

    @OneToMany(mappedBy = "customer")
    private List<Scoring> scoringList;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

}
