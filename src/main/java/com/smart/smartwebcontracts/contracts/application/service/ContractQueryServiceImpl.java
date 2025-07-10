package com.smart.smartwebcontracts.contracts.application.service;

import com.smart.smartwebcontracts.contracts.domain.model.Contract;
import com.smart.smartwebcontracts.contracts.domain.repository.ContractRepository;
import com.smart.smartwebcontracts.contracts.infrastructure.blockchain.BlockchainContractAdapter;
import com.smart.smartwebcontracts.contracts.infrastructure.blockchain.dto.HashRecordDTO;
import com.smart.smartwebcontracts.contracts.infrastructure.blockchain.dto.SmartContractDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractQueryServiceImpl {

    private final ContractRepository contractRepository;
    private final BlockchainContractAdapter blockchainAdapter;


    public Contract handleGetContractById(UUID contractId) {
        return contractRepository.findById(contractId)
                .orElseThrow(() -> new IllegalArgumentException("Contract not found"));
    }

    public List<Contract> handleGetActiveContractsByUser(UUID userId) {
        List<Contract> asClient = contractRepository.findByClientId(userId);
        List<Contract> asDeveloper = contractRepository.findByDeveloperId(userId);

        asClient.removeIf(contract -> !contract.getStatus().equals("EN_PROCESO"));
        asDeveloper.removeIf(contract -> !contract.getStatus().equals("EN_PROCESO"));

        asClient.addAll(asDeveloper);
        return asClient;
    }
    public List<HashRecordDTO> handleGetAllHashRecords() {
        return blockchainAdapter.getAllStoredHashes();
    }

    public List<SmartContractDTO> handleGetAllSmartContracts() {
        return blockchainAdapter.getAllSmartContracts();
    }
    public List<Contract> handleGetAllContracts() {
        return contractRepository.findAll();
    }


}
