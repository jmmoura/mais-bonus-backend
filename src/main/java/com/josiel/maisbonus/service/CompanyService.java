package com.josiel.maisbonus.service;

import com.josiel.maisbonus.model.Company;
import com.josiel.maisbonus.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyService {

    private CompanyRepository companyRepository;

    public Company findByCnpj(String cnpj) {
        return companyRepository.findByCnpj(cnpj);
    }
}
