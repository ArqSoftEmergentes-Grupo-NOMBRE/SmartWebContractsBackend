package com.smart.smartwebcontracts.contracts.infrastructure.blockchain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class SmartContractDTO {
    private String contractId;
    private String clientId;
    private String developerId;
    private String webServiceId;
    private BigInteger startDate;
    private String status;
}