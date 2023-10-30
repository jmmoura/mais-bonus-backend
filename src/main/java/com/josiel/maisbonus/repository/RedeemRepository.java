package com.josiel.maisbonus.repository;

import com.josiel.maisbonus.model.Company;
import com.josiel.maisbonus.model.Customer;
import com.josiel.maisbonus.model.Redeem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RedeemRepository extends JpaRepository<Redeem, Long> {

    Optional<Redeem> findByCompanyIdAndCustomer(Long companyId, Customer customer);

    Optional<Redeem> findByCodeAndCompany(String code, Company company);

}
