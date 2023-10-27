package com.josiel.maisbonus.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    private String cpf;

    private String name;

    private String email;

    private String phone;

    private Long companyID;

    private UserDTO user;

}
