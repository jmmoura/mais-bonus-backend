package com.josiel.maisbonus.dto.mapper;

import com.josiel.maisbonus.dto.CompanyDTO;
import com.josiel.maisbonus.dto.UserDTO;
import com.josiel.maisbonus.enums.Role;
import com.josiel.maisbonus.model.Company;
import com.josiel.maisbonus.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class CompanyMapper {

    public Company toEntity(CompanyDTO companyDTO) {
        User user = User.builder()
                .username(companyDTO.getEmail())
                .password(companyDTO.getUser().getPassword())
                .role(Role.COMPANY)
                .build();

        return Company.builder()
                .name(companyDTO.getName())
                .email(companyDTO.getEmail())
                .phone(companyDTO.getPhone())
                .cnpj(companyDTO.getCnpj())
                .user(user)
                .build();
    }

    public CompanyDTO toDTO(Company company) {
        UserDTO userDTO = UserDTO.builder()
                .userName(company.getUser().getUsername())
                .build();

        return CompanyDTO.builder()
                .cnpj(company.getCnpj())
                .name(company.getName())
                .email(company.getEmail())
                .phone(company.getPhone())
                .user(userDTO)
                .build();
    }

    public List<CompanyDTO> toDTOList(List<Company> companyList) {
        return companyList.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
