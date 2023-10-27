package com.josiel.maisbonus.service;

import com.josiel.maisbonus.dto.CompanyDTO;
import com.josiel.maisbonus.dto.mapper.CompanyMapper;
import com.josiel.maisbonus.enums.Role;
import com.josiel.maisbonus.model.Company;
import com.josiel.maisbonus.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompanyService {

    private CompanyRepository companyRepository;

    private CompanyMapper companyMapper;

    private PasswordEncoder passwordEncoder;

    public List<CompanyDTO> list() {
        return companyMapper.toDTOList(companyRepository.findAll());
    }

    public CompanyDTO findById(Long id) {
        return companyRepository.findById(id).map(companyMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Company ID " + id + " not found"));
    }

    public CompanyDTO create(CompanyDTO companyDTO) {
        companyDTO.getUser().setPassword(passwordEncoder.encode(companyDTO.getUser().getPassword()));
        Company company = companyMapper.toEntity(companyDTO);
        return companyMapper.toDTO(companyRepository.save(company));
    }

    public CompanyDTO update(Long id, CompanyDTO companyDTO) {
        return companyRepository.findById(id)
                .map(company -> {
                    company.setName(companyDTO.getName());
                    company.setEmail(companyDTO.getEmail());
                    company.setPhone(companyDTO.getPhone());
                    return companyMapper.toDTO(companyRepository.save(company));
                })
                .orElseThrow(() -> new IllegalArgumentException("User ID " + id + " not found"));
    }

    public CompanyDTO updatePassword(Long id, CompanyDTO companyDTO) {
        return companyRepository.findById(id)
                .map(company -> {
                    company.getUser().setPassword(passwordEncoder.encode(companyDTO.getUser().getPassword()));
                    return companyMapper.toDTO(companyRepository.save(company));
                })
                .orElseThrow(() -> new IllegalArgumentException("User ID " + id + " not found"));
    }

    public void delete(Long id) {
        companyRepository.delete(companyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User ID " + id + " not found")));
    }
}
