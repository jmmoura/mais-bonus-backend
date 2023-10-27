package com.josiel.maisbonus.service;

import com.josiel.maisbonus.dto.CustomerDTO;
import com.josiel.maisbonus.dto.mapper.CompanyMapper;
import com.josiel.maisbonus.dto.mapper.CustomerMapper;
import com.josiel.maisbonus.model.Company;
import com.josiel.maisbonus.model.Customer;
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

    public CustomerDTO create(CustomerDTO customerDTO) {
        customerDTO.getUser().setPassword(passwordEncoder.encode(customerDTO.getUser().getPassword()));
        Customer customer = customerRepository.save(customerMapper.toEntity(customerDTO));

        Company company = companyMapper.toEntity(companyService.findById(customerDTO.getCompanyID()));
        List<Company> companies = new ArrayList<>();
        companies.add(company);
        customer.setCompanies(companies);

        long customerID = 1000 + customer.getId();
        customer.setPersonalId(Long.toString(customerID));

        return customerMapper.toDTO(customerRepository.save(customer));
    }

    public CustomerDTO update(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setName(customerDTO.getName());
                    customer.setEmail(customerDTO.getEmail());
                    customer.setPhone(customerDTO.getPhone());
                    return customerMapper.toDTO(customerRepository.save(customer));
                })
                .orElseThrow(() -> new IllegalArgumentException("Customer ID " + id + " not found"));
    }

    public CustomerDTO updatePassword(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.getUser().setPassword(passwordEncoder.encode(customerDTO.getUser().getPassword()));
                    return customerMapper.toDTO(customerRepository.save(customer));
                })
                .orElseThrow(() -> new IllegalArgumentException("Customer ID " + id + " not found"));
    }

    public void delete(Long id) {
        customerRepository.delete(customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Customer ID " + id + " not found")));
    }
}
