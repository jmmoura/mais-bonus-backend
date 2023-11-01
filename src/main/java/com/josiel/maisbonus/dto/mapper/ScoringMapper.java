package com.josiel.maisbonus.dto.mapper;

import com.josiel.maisbonus.dto.ScoringDTO;
import com.josiel.maisbonus.model.Scoring;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ScoringMapper {

    private CompanyMapper companyMapper;

    public Scoring toEntity(ScoringDTO scoringDTO) {
        return Scoring.builder()
                .description(scoringDTO.getDescription())
                .purchaseAmount(scoringDTO.getPurchaseAmount())
                .cashbackAmount(scoringDTO.getCashbackAmount())
                .timestamp(scoringDTO.getTimestamp())
                .build();
    }

    public ScoringDTO toDTO(Scoring scoring) {
        return ScoringDTO.builder()
                .description(scoring.getDescription())
                .purchaseAmount(scoring.getPurchaseAmount())
                .cashbackAmount(scoring.getCashbackAmount())
                .timestamp(scoring.getTimestamp())
                .company(companyMapper.toDTO(scoring.getCompany()))
                .customerPersonalId(scoring.getCustomer().getPersonalId())
                .build();
    }

    public List<ScoringDTO> toDTOList(List<Scoring> scoringList) {
        return scoringList.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
