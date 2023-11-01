package com.josiel.maisbonus.dto.mapper;

import com.josiel.maisbonus.dto.RedeemDTO;
import com.josiel.maisbonus.model.Redeem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class RedeemMapper {

    private CustomerMapper customerMapper;

    public Redeem toEntity(RedeemDTO redeemDTO) {
        return Redeem.builder()
                .code(redeemDTO.getCode())
                .amount(redeemDTO.getAmount())
                .timestamp(redeemDTO.getTimestamp())
                .build();
    }

    public RedeemDTO toDTO(Redeem redeem) {
        return RedeemDTO.builder()
                .code(redeem.getCode())
                .amount(redeem.getAmount())
                .timestamp(redeem.getTimestamp())
                .companyId(redeem.getCompany().getId())
                .customer(customerMapper.toDTO(redeem.getCustomer()))
                .build();
    }

    public List<RedeemDTO> toDTOList(List<Redeem> redeemList) {
        return redeemList.stream().map(this::toDTO).collect(Collectors.toList());
    }

}
