package com.josiel.maisbonus.authentication.controller;

import com.josiel.maisbonus.authentication.dto.AuthenticationDTO;
import com.josiel.maisbonus.authentication.dto.CredentialsDTO;
import com.josiel.maisbonus.authentication.service.SecurityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping(AuthenticationController.URL)
@RestController
public class AuthenticationController {

    public static final String URL = "api/v1/sign-in";

    private final SecurityService securityService;

    @PostMapping
    public AuthenticationDTO login(@RequestBody CredentialsDTO credentialsDTO) {
        final String username = credentialsDTO.getUsername();
        final String password = credentialsDTO.getPassword();

        final String token = securityService.authenticate(username, password);

        return new AuthenticationDTO(token);
    }
}
