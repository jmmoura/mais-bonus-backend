package com.josiel.maisbonus.controller;

import com.josiel.maisbonus.dto.CustomerDTO;
import com.josiel.maisbonus.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CustomerController.URL)
@AllArgsConstructor
public class CustomerController {

    public static final String URL = "api/v1/customer";

    private CustomerService customerService;

    @GetMapping
    public List<CustomerDTO> list() {
        return customerService.list();
    }

    @GetMapping("/{id}")
    public CustomerDTO findById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO create(@RequestBody CustomerDTO customerDTO) {
        return customerService.create(customerDTO);
    }

    @PutMapping("/{id}")
    public CustomerDTO update(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.update(id, customerDTO);
    }

    @PutMapping("/{id}/password")
    public CustomerDTO updatePassword(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return customerService.updatePassword(id, customerDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        customerService.delete(id);
    }

}
