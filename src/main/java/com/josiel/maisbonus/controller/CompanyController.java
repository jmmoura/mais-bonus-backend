package com.josiel.maisbonus.controller;

import com.josiel.maisbonus.dto.CompanyDTO;
import com.josiel.maisbonus.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CompanyController.URL)
@AllArgsConstructor
public class CompanyController {

    public static final String URL = "api/v1/company";

    private CompanyService companyService;

    @GetMapping
    public List<CompanyDTO> list() {
        return companyService.list();
    }

    @GetMapping("/{id}")
    public CompanyDTO findById(@PathVariable Long id) {
        return companyService.findById(id);
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDTO create(@RequestBody CompanyDTO companyDTO) {
        return companyService.create(companyDTO);
    }

    @PutMapping
    public CompanyDTO update(@RequestBody CompanyDTO companyDTO) {
        return companyService.update(companyDTO);
    }

    @PutMapping("/password")
    public CompanyDTO updatePassword(@RequestBody CompanyDTO companyDTO) {
        return companyService.updatePassword(companyDTO);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        companyService.delete();
    }

}
