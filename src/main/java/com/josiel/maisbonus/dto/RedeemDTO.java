package com.josiel.maisbonus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedeemDTO {

    private String code;

    private double amount;

    private LocalDateTime timestamp;

    private CustomerDTO customer;

    private CompanyDTO company;
}
