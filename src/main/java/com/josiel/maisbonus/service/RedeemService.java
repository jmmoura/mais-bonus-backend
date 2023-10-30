package com.josiel.maisbonus.service;

import com.josiel.maisbonus.authentication.service.SecurityService;
import com.josiel.maisbonus.dto.CompanyDTO;
import com.josiel.maisbonus.dto.CustomerDTO;
import com.josiel.maisbonus.dto.RedeemDTO;
import com.josiel.maisbonus.dto.ScoringDTO;
import com.josiel.maisbonus.dto.mapper.CompanyMapper;
import com.josiel.maisbonus.dto.mapper.RedeemMapper;
import com.josiel.maisbonus.model.Company;
import com.josiel.maisbonus.model.Customer;
import com.josiel.maisbonus.model.Redeem;
import com.josiel.maisbonus.model.User;
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

    private SecurityService securityService;

    private RedeemMapper redeemMapper;

    private CompanyMapper companyMapper;

    public RedeemDTO find(String code) {
        return redeemRepository.findByCodeAndCompany(code, getCompany()).map(redeemMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Redeem Code " + code + " not found."));
    }

    public RedeemDTO create(RedeemDTO redeemDTO) {
        User user = securityService.getCurrentUser();
        Customer customer = customerService.findByUser(user);
        Optional<Redeem> optionalRedeem = redeemRepository.findByCompanyIdAndCustomer(redeemDTO.getCompany().getId(),
                customer);

        Redeem redeem;
        if (optionalRedeem.isPresent()) {
            redeem = optionalRedeem.get();
            redeem.setAmount(redeemDTO.getAmount());
        } else {
            redeem = redeemMapper.toEntity(redeemDTO);

            redeem.setCustomer(customer);

            Company company = companyMapper.toEntity(companyService.findById(redeemDTO.getCompany().getId()));
            redeem.setCompany(company);
        }

        redeem.setTimestamp(LocalDateTime.now());

        String code = getRandomCode(redeem.getCompany());
        redeem.setCode(code);

        return redeemMapper.toDTO(redeemRepository.save(redeem));
    }

    private String getRandomCode(Company company) {
        SecureRandom secureRandom = new SecureRandom();
        int randomInt = secureRandom.nextInt(10000 - 1000) + 1000;
        String code = Integer.toString(randomInt);

        while (redeemRepository.findByCodeAndCompany(code, company).isPresent()) {
            randomInt = secureRandom.nextInt(10000 - 1000) + 1000;
            code = Integer.toString(randomInt);
        }

        return Integer.toString(randomInt);
    }

    public RedeemDTO withdraw(RedeemDTO redeemDTO) {
        Redeem redeem = redeemRepository.findByCodeAndCompany(redeemDTO.getCode(), getCompany())
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

    private Company getCompany() {
        User user = securityService.getCurrentUser();
        return companyService.findByUser(user);
    }

}
