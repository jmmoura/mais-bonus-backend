package com.josiel.maisbonus.authentication.service;

import com.josiel.maisbonus.authentication.dto.AuthenticationDTO;
import com.josiel.maisbonus.model.Company;
import com.josiel.maisbonus.model.Customer;
import com.josiel.maisbonus.repository.UserRepository;
import com.josiel.maisbonus.authentication.utils.JWTUtils;
import com.josiel.maisbonus.enums.Role;
import com.josiel.maisbonus.model.User;
import com.josiel.maisbonus.service.CompanyService;
import com.josiel.maisbonus.service.CustomerService;
import io.jsonwebtoken.Claims;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements UserDetailsService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final CompanyService companyService;

    private final CustomerService customerService;

    @Autowired
    public SecurityService(@Lazy AuthenticationManager authenticationManager, UserRepository userRepository,
                           @Lazy CompanyService companyService, @Lazy CustomerService customerService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.companyService = companyService;
        this.customerService = customerService;
    }

    @Transactional
    public AuthenticationDTO authenticate(final String username, final String password) {
        final Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username, password)
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final User user = loadUserByUsername(username);

        Long companyId;
        String customerPersonalId = null;
        if (user.getRole().equals(Role.CUSTOMER)) {
            Customer customer = customerService.findByUser(user);
            companyId = customer.getCompanies().get(0).getId();
            customerPersonalId = customer.getPersonalId();
        } else {
            Company company = companyService.findByUser(user);
            companyId = company.getId();
        }

        return AuthenticationDTO.builder()
                .token(JWTUtils.generateToken(user.getId(), user.getUsername(), user.getRole()))
                .role(user.getRole())
                .companyId(companyId)
                .customerPersonalId(customerPersonalId)
                .build();
    }

    @Transactional
    public void authenticate(final String token) {
        final Claims claims = JWTUtils.parseToken(token);

        User user = new User();
        user.setId(Long.parseLong(claims.getSubject()));
        user.setPassword("");
        user.setUsername(claims.get(JWTUtils.TOKEN_CLAIM_USERNAME).toString());
        user.setRole(Role.valueOf(claims.get(JWTUtils.TOKEN_CLAIM_ROLES).toString()));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
        );
    }

    @Transactional
    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Transactional
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
    }

}
