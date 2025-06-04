package com.smart.smartwebcontracts.contracts.application.service;

import com.smart.smartwebcontracts.contracts.domain.model.Contract;
import com.smart.smartwebcontracts.contracts.domain.repository.ContractRepository;
import com.smart.smartwebcontracts.contracts.infrastructure.blockchain.BlockchainContractAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractCommandServiceImpl {

    private final ContractRepository contractRepository;
    private final BlockchainContractAdapter blockchainContractAdapter; // <- nuevo


    public Contract handleCreateContract(UUID clientId, UUID developerId, UUID webServiceId) {
        Contract contract = Contract.builder()
                .id(UUID.randomUUID())
                .clientId(clientId)
                .developerId(developerId)
                .webServiceId(webServiceId)
                .startDate(LocalDate.now())
                .status("CREADO")
                .build();
        return contractRepository.save(contract);
    }

    public Contract handleSignContract(UUID contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new IllegalArgumentException("Contract not found"));
        contract.signContract();
        return contractRepository.save(contract);
    }

    public Contract handleFinalizeContract(UUID contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new IllegalArgumentException("Contract not found"));
        contract.finalizeContract();
        return contractRepository.save(contract);
    }

    // ContractCommandServiceImpl.java
    public String registerContractHashOnBlockchain(String hash) {
        return blockchainContractAdapter.registerContractHashOnBlockchain(hash);
    }

}
