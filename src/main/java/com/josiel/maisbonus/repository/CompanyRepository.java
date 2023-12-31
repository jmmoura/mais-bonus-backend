package com.josiel.maisbonus.repository;

import com.josiel.maisbonus.model.Company;
import com.josiel.maisbonus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByUser(User user);

}
