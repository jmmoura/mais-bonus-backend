package com.josiel.maisbonus.service;

import com.josiel.maisbonus.authentication.service.SecurityService;
import com.josiel.maisbonus.dto.CustomerDTO;
import com.josiel.maisbonus.dto.mapper.CompanyMapper;
import com.josiel.maisbonus.dto.mapper.CustomerMapper;
import com.josiel.maisbonus.model.Company;
import com.josiel.maisbonus.model.Customer;
import com.josiel.maisbonus.model.User;
import com.josiel.maisbonus.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;

    private CustomerMapper customerMapper;

    private CompanyService companyService;

    private SecurityService securityService;

    private CompanyMapper companyMapper;

    private PasswordEncoder passwordEncoder;

    public List<CustomerDTO> list() {
        return customerMapper.toDTOList(customerRepository.findAll());
    }

    public CustomerDTO findById(Long id) {
        return customerRepository.findById(id).map(customerMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Customer ID " + id + " not found"));
    }

    public Customer findByPersonalId(String personalId) {
        return customerRepository.findByPersonalId(personalId)
                .orElseThrow(() -> new IllegalArgumentException("Customer Personal ID " + personalId + " not found"));
    }

    public Customer findByUser(User user) {
        return customerRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("User ID " + user.getId() + " not found"));
    }

    public CustomerDTO create(CustomerDTO customerDTO) {
        customerDTO.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        Customer customer = customerRepository.save(customerMapper.toEntity(customerDTO));

        Company company = companyMapper.toEntity(companyService.findById(customerDTO.getCompanyId()));
        List<Company> companies = new ArrayList<>();
        companies.add(company);
        customer.setCompanies(companies);

        long customerID = 10000 + customer.getId();
        customer.setPersonalId(Long.toString(customerID));

        return customerMapper.toDTO(customerRepository.save(customer));
    }

    public CustomerDTO update(CustomerDTO customerDTO) {
        User user = securityService.getCurrentUser();
        return customerRepository.findByUser(user)
                .map(customer -> {
                    customer.setName(customerDTO.getName());
                    customer.setEmail(customerDTO.getEmail());
                    customer.setPhone(customerDTO.getPhone());
                    return customerMapper.toDTO(customerRepository.save(customer));
                })
                .orElseThrow(() -> new IllegalArgumentException("User ID " + user.getId() + " not found"));
    }

    public CustomerDTO updatePassword(CustomerDTO customerDTO) {
        User user = securityService.getCurrentUser();
        return customerRepository.findByUser(user)
                .map(customer -> {
                    customer.getUser().setPassword(passwordEncoder.encode(customerDTO.getPassword()));
                    return customerMapper.toDTO(customerRepository.save(customer));
                })
                .orElseThrow(() -> new IllegalArgumentException("User ID " + user.getId() + " not found"));
    }

    public void delete() {
        User user = securityService.getCurrentUser();
        customerRepository.delete(customerRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("User ID " + user.getId() + " not found")));
    }

}
