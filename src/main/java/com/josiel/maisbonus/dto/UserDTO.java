package com.josiel.maisbonus.dto;

import com.josiel.maisbonus.enums.UserType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class UserDTO {

    private UserType type;

    private String cpf;

    private String cnpj;

    private String name;

    private String email;

    private String phone;

    private String password;

}
