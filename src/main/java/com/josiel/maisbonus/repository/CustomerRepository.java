package com.josiel.maisbonus.repository;

import com.josiel.maisbonus.model.Customer;
import com.josiel.maisbonus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByPersonalId(String personalId);

    Optional<Customer> findByUser(User user);

}
