package com.josiel.maisbonus.dto.mapper;

import com.josiel.maisbonus.dto.CustomerDTO;
import com.josiel.maisbonus.dto.UserDTO;
import com.josiel.maisbonus.enums.Role;
import com.josiel.maisbonus.model.Customer;
import com.josiel.maisbonus.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class CustomerMapper {

    public Customer toEntity(CustomerDTO customerDTO) {
        User user = User.builder()
                .username(customerDTO.getEmail())
                .password(customerDTO.getPassword())
                .role(Role.CUSTOMER)
                .build();

        return Customer.builder()
                .id(customerDTO.getId())
                .name(customerDTO.getName())
                .email(customerDTO.getEmail())
                .phone(customerDTO.getPhone())
                .cpf(customerDTO.getCpf())
                .user(user)
                .build();
    }

    public CustomerDTO toDTO(Customer customer) {
        UserDTO userDTO = UserDTO.builder()
                .userName(customer.getUser().getUsername())
                .build();

        return CustomerDTO.builder()
                .id(customer.getId())
                .cpf(customer.getCpf())
                .name(customer.getName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .personalId(customer.getPersonalId())
                .build();
    }

    public List<CustomerDTO> toDTOList(List<Customer> customerList) {
        return customerList.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
