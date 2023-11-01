package com.josiel.maisbonus.authentication.dto;

import com.josiel.maisbonus.enums.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AuthenticationDTO {

    private String token;

    private Role role;

}
