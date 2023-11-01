package com.josiel.maisbonus.service;

import com.josiel.maisbonus.authentication.service.SecurityService;
import com.josiel.maisbonus.dto.ScoringDTO;
import com.josiel.maisbonus.dto.mapper.ScoringMapper;
import com.josiel.maisbonus.model.Company;
import com.josiel.maisbonus.model.Customer;
import com.josiel.maisbonus.model.Scoring;
import com.josiel.maisbonus.model.User;
import com.josiel.maisbonus.repository.ScoringRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class ScoringService {

    private ScoringRepository scoringRepository;

    private CustomerService customerService;

    private CompanyService companyService;

    private SecurityService securityService;

    private ScoringMapper scoringMapper;

    public List<ScoringDTO> list(Long companyId) {
        User user = securityService.getCurrentUser();
        Customer customer = customerService.findByUser(user);
        return scoringMapper.toDTOList(scoringRepository.findByCompanyIdAndCustomer(companyId, customer));
    }

    public ScoringDTO findById(Long id) {
        return scoringRepository.findById(id).map(scoringMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Scoring ID " + id + " not found"));
    }

    public ScoringDTO create(ScoringDTO scoringDTO) {
        scoringDTO.setTimestamp(LocalDateTime.now());
        Scoring scoring = scoringMapper.toEntity(scoringDTO);

        Customer customer = customerService.findByPersonalId(scoringDTO.getCustomerPersonalId());
        scoring.setCustomer(customer);

        User user = securityService.getCurrentUser();
        Company company = companyService.findByUser(user);
        scoring.setCompany(company);

        return scoringMapper.toDTO(scoringRepository.save(scoring));
    }
}
