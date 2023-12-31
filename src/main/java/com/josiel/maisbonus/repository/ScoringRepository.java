package com.josiel.maisbonus.repository;

import com.josiel.maisbonus.model.Customer;
import com.josiel.maisbonus.model.Scoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoringRepository extends JpaRepository<Scoring, Long> {

    List<Scoring> findByCompanyIdAndCustomer(Long companyId, Customer customer);

}
