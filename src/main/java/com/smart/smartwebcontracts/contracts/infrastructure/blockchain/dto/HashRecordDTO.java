package com.smart.smartwebcontracts.contracts.infrastructure.blockchain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class HashRecordDTO {
    private String hash;
    private BigInteger timestamp;
    private String sender;
}
