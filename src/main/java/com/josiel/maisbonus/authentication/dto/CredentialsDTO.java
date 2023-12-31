package com.josiel.maisbonus.authentication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialsDTO {

//    @NotNull(message = "Username is required")
//    @Size(message = "Username length must be equal to or lower than 50", min = 1, max = 50)
    private String username;

//    @NotNull(message = "Password is required")
//    @Size(message = "Password length must be between 8 and 50", min = 8, max = 50)
    private String password;
}
