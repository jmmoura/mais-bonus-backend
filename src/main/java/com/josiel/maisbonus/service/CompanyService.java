package com.josiel.maisbonus.service;

import com.josiel.maisbonus.authentication.service.SecurityService;
import com.josiel.maisbonus.dto.CompanyDTO;
import com.josiel.maisbonus.dto.mapper.CompanyMapper;
import com.josiel.maisbonus.model.Company;
import com.josiel.maisbonus.model.User;
import com.josiel.maisbonus.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompanyService {

    private CompanyRepository companyRepository;

    private SecurityService securityService;

    private CompanyMapper companyMapper;

    private PasswordEncoder passwordEncoder;

    public List<CompanyDTO> list() {
        return companyMapper.toDTOList(companyRepository.findAll());
    }

    public CompanyDTO findById(Long id) {
        return companyRepository.findById(id).map(companyMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Company ID " + id + " not found"));
    }

    public Company findByUser(User user) {
        return companyRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("User ID " + user.getId() + " not found"));
    }

    public CompanyDTO create(CompanyDTO companyDTO) {
        companyDTO.setPassword(passwordEncoder.encode(companyDTO.getPassword()));
        Company company = companyMapper.toEntity(companyDTO);
        return companyMapper.toDTO(companyRepository.save(company));
    }

    public CompanyDTO update(CompanyDTO companyDTO) {
        User user = securityService.getCurrentUser();
        return companyRepository.findByUser(user)
                .map(company -> {
                    company.setName(companyDTO.getName());
                    company.setEmail(companyDTO.getEmail());
                    company.setPhone(companyDTO.getPhone());
                    return companyMapper.toDTO(companyRepository.save(company));
                })
                .orElseThrow(() -> new IllegalArgumentException("User ID " + user.getId() + " not found"));
    }

    public CompanyDTO updatePassword(CompanyDTO companyDTO) {
        User user = securityService.getCurrentUser();
        return companyRepository.findByUser(user)
                .map(company -> {
                    company.getUser().setPassword(passwordEncoder.encode(companyDTO.getPassword()));
                    return companyMapper.toDTO(companyRepository.save(company));
                })
                .orElseThrow(() -> new IllegalArgumentException("User ID " + user.getId() + " not found"));
    }

    public void delete() {
        User user = securityService.getCurrentUser();
        companyRepository.delete(companyRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("User ID " + user.getId() + " not found")));
    }

}
