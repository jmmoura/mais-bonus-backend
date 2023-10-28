package com.josiel.maisbonus.controller;

import com.josiel.maisbonus.dto.ScoringDTO;
import com.josiel.maisbonus.service.ScoringService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ScoringController.URL)
@AllArgsConstructor
public class ScoringController {

    public static final String URL = "api/v1/scoring";

    private ScoringService scoringService;

    @GetMapping
    public List<ScoringDTO> list(@RequestParam Long companyId, @RequestParam Long customerId) {
        return scoringService.list(companyId, customerId);
    }

    @GetMapping("/{id}")
    public ScoringDTO findById(@PathVariable Long id) {
        return scoringService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScoringDTO create(@RequestBody ScoringDTO scoringDTO) {
        return scoringService.create(scoringDTO);
    }

}
