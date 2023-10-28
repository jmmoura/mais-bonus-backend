package com.josiel.maisbonus.controller;

import com.josiel.maisbonus.dto.RedeemDTO;
import com.josiel.maisbonus.service.RedeemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RedeemController.URL)
@AllArgsConstructor
public class RedeemController {

    public static final String URL = "api/v1/redeem";

    private RedeemService redeemService;

    @GetMapping
    public RedeemDTO find(@RequestParam String code, @RequestParam Long companyId, @RequestParam Long customerId) {
        return redeemService.find(code, companyId, customerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RedeemDTO create(@RequestBody RedeemDTO redeemDTO) {
        return redeemService.create(redeemDTO);
    }

    @PostMapping("/withdraw")
    @ResponseStatus(HttpStatus.CREATED)
    public RedeemDTO withdraw(@RequestBody RedeemDTO redeemDTO) {
        return redeemService.withdraw(redeemDTO);
    }

}
