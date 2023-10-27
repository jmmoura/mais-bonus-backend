package com.josiel.maisbonus.dto;

import com.josiel.maisbonus.model.Company;
import com.josiel.maisbonus.model.Customer;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoringDTO {

    private String description;

    private double purchaseAmount;

    private double cashbackAmount;

    private LocalDateTime timestamp;

    private CustomerDTO customerDTO;

    private CompanyDTO companyDTO;

}
