package com.josiel.maisbonus.repository;

import com.josiel.maisbonus.model.Redeem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RedeemRepository extends JpaRepository<Redeem, Long> {

    Optional<Redeem> findByCodeAndCompanyIdAndCustomerId(String code, Long companyId, Long customerId);

    Optional<Redeem> findByCompanyIdAndCustomerId(Long companyId, Long customerId);

}
