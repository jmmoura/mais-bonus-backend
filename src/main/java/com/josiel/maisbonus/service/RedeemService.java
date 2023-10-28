package com.josiel.maisbonus.service;

import com.josiel.maisbonus.dto.CompanyDTO;
import com.josiel.maisbonus.dto.CustomerDTO;
import com.josiel.maisbonus.dto.RedeemDTO;
import com.josiel.maisbonus.dto.ScoringDTO;
import com.josiel.maisbonus.dto.mapper.CompanyMapper;
import com.josiel.maisbonus.dto.mapper.CustomerMapper;
import com.josiel.maisbonus.dto.mapper.RedeemMapper;
import com.josiel.maisbonus.model.Company;
import com.josiel.maisbonus.model.Customer;
import com.josiel.maisbonus.model.Redeem;
import com.josiel.maisbonus.repository.RedeemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RedeemService {

    private RedeemRepository redeemRepository;

    private ScoringService scoringService;

    private CompanyService companyService;

    private CustomerService customerService;

    private RedeemMapper redeemMapper;

    private CompanyMapper companyMapper;

    private CustomerMapper customerMapper;

    public RedeemDTO find(String code, Long companyId, Long customerId) {
        return redeemRepository.findByCodeAndCompanyIdAndCustomerId(code, companyId, customerId).map(redeemMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Redeem Code " + code + " not found."));
    }

    public RedeemDTO create(RedeemDTO redeemDTO) {
        Optional<Redeem> optionalRedeem = redeemRepository.findByCompanyIdAndCustomerId(redeemDTO.getCompany().getId(),
                redeemDTO.getCustomer().getId());

        Redeem redeem;
        if (optionalRedeem.isPresent()) {
            redeem = optionalRedeem.get();
            redeem.setAmount(redeemDTO.getAmount());
        } else {
            redeem = redeemMapper.toEntity(redeemDTO);

            Customer customer = customerMapper.toEntity(customerService.findById(redeemDTO.getCustomer().getId()));
            redeem.setCustomer(customer);

            Company company = companyMapper.toEntity(companyService.findById(redeemDTO.getCompany().getId()));
            redeem.setCompany(company);
        }

        redeem.setTimestamp(LocalDateTime.now());

        SecureRandom secureRandom = new SecureRandom();
        int randomInt = secureRandom.nextInt(10000 - 1000) + 1000;
        String code = Integer.toString(randomInt);
        redeem.setCode(code);

        return redeemMapper.toDTO(redeemRepository.save(redeem));
    }

    public RedeemDTO withdraw(RedeemDTO redeemDTO) {
        Redeem redeem = redeemRepository.findByCodeAndCompanyIdAndCustomerId(redeemDTO.getCode(),
                redeemDTO.getCompany().getId(), redeemDTO.getCustomer().getId())
                .orElseThrow(() -> new IllegalArgumentException("Redeem Code " + redeemDTO.getCode() + " not found."));

        CompanyDTO companyDTO = CompanyDTO.builder().id(redeem.getCompany().getId()).build();

        CustomerDTO customerDTO = CustomerDTO.builder().personalId(redeem.getCustomer().getPersonalId()).build();

        ScoringDTO scoringDTO = ScoringDTO.builder()
                .description("Resgate")
                .cashbackAmount(redeem.getAmount() * -1)
                .timestamp(LocalDateTime.now())
                .company(companyDTO)
                .customer(customerDTO)
                .build();

        scoringService.create(scoringDTO);

        redeemRepository.delete(redeem);

        return redeemMapper.toDTO(redeem);
    }

}
