package com.josiel.maisbonus.dto.mapper;

import com.josiel.maisbonus.dto.UserDTO;
import com.josiel.maisbonus.enums.UserType;
import com.josiel.maisbonus.model.Company;
import com.josiel.maisbonus.model.Customer;
import com.josiel.maisbonus.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class UserMapper {

    private PasswordEncoder passwordEncoder;

    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .phone(userDTO.getPhone())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .customer(createCustomer(userDTO.getCpf()))
                .company(createCompany(userDTO.getCnpj()))
                .role("ROLE_" + userDTO.getType())
                .build();
    }

    private Customer createCustomer(String cpf) {
        return cpf == null ? null : Customer.builder().cpf(cpf).build();
    }

    private Company createCompany(String cnpj) {
        return cnpj == null ? null : Company.builder().cnpj(cnpj).build();
    }

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .type(user.getCompany() != null ? UserType.COMPANY : UserType.CUSTOMER)
                .name(user.getName())
                .cnpj(user.getCompany() != null ? user.getCompany().getCnpj() : null)
                .cpf(user.getCustomer() != null ? user.getCustomer().getCpf() : null)
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }

    public List<UserDTO> toDTOList(List<User> userList) {
        return userList.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
