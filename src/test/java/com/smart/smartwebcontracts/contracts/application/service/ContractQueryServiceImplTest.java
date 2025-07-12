// src/test/java/com/smart/smartwebcontracts/contracts/application/service/ContractQueryServiceImplTest.java
package com.smart.smartwebcontracts.contracts.application.service;

import com.smart.smartwebcontracts.contracts.domain.model.Contract;
import com.smart.smartwebcontracts.contracts.domain.repository.ContractRepository;
import com.smart.smartwebcontracts.contracts.infrastructure.blockchain.BlockchainContractAdapter;
import com.smart.smartwebcontracts.contracts.infrastructure.blockchain.dto.HashRecordDTO;
import com.smart.smartwebcontracts.contracts.infrastructure.blockchain.dto.SmartContractDTO;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContractQueryServiceImplTest {

    @Test
    void handleGetContractById_returnsContract() {
        ContractRepository repo = mock(ContractRepository.class);
        BlockchainContractAdapter adapter = mock(BlockchainContractAdapter.class);
        UUID id = UUID.randomUUID();
        Contract contract = new Contract();
        when(repo.findById(id)).thenReturn(Optional.of(contract));

        ContractQueryServiceImpl service = new ContractQueryServiceImpl(repo, adapter);
        Contract result = service.handleGetContractById(id);
        assertNotNull(result);
    }

    @Test
    void handleGetContractById_notFound_throwsException() {
        ContractRepository repo = mock(ContractRepository.class);
        BlockchainContractAdapter adapter = mock(BlockchainContractAdapter.class);
        UUID id = UUID.randomUUID();
        when(repo.findById(id)).thenReturn(Optional.empty());

        ContractQueryServiceImpl service = new ContractQueryServiceImpl(repo, adapter);
        assertThrows(IllegalArgumentException.class, () -> service.handleGetContractById(id));
    }

    @Test
    void handleGetActiveContractsByUser_returnsActiveContracts() {
        ContractRepository repo = mock(ContractRepository.class);
        BlockchainContractAdapter adapter = mock(BlockchainContractAdapter.class);
        UUID userId = UUID.randomUUID();
        Contract activeAsClient = new Contract();
        activeAsClient.setStatus("ACTIVO");
        Contract inactiveAsClient = new Contract();
        inactiveAsClient.setStatus("INACTIVO");
        Contract activeAsDev = new Contract();
        activeAsDev.setStatus("ACTIVO");

        when(repo.findByClientId(userId)).thenReturn(new ArrayList<>(Arrays.asList(activeAsClient, inactiveAsClient)));
        when(repo.findByDeveloperId(userId)).thenReturn(new ArrayList<>(Collections.singletonList(activeAsDev)));

        ContractQueryServiceImpl service = new ContractQueryServiceImpl(repo, adapter);
        List<Contract> result = service.handleGetActiveContractsByUser(userId);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(c -> "ACTIVO".equals(c.getStatus())));
    }

    @Test
    void handleGetAllHashRecords_returnsList() {
        ContractRepository repo = mock(ContractRepository.class);
        BlockchainContractAdapter adapter = mock(BlockchainContractAdapter.class);
        List<HashRecordDTO> hashes = Arrays.asList(new HashRecordDTO(), new HashRecordDTO());
        when(adapter.getAllStoredHashes()).thenReturn(hashes);

        ContractQueryServiceImpl service = new ContractQueryServiceImpl(repo, adapter);
        List<HashRecordDTO> result = service.handleGetAllHashRecords();
        assertEquals(2, result.size());
    }

    @Test
    void handleGetAllSmartContracts_returnsList() {
        ContractRepository repo = mock(ContractRepository.class);
        BlockchainContractAdapter adapter = mock(BlockchainContractAdapter.class);
        List<SmartContractDTO> contracts = Arrays.asList(new SmartContractDTO(), new SmartContractDTO());
        when(adapter.getAllSmartContracts()).thenReturn(contracts);

        ContractQueryServiceImpl service = new ContractQueryServiceImpl(repo, adapter);
        List<SmartContractDTO> result = service.handleGetAllSmartContracts();
        assertEquals(2, result.size());
    }

    @Test
    void handleGetAllContracts_returnsList() {
        ContractRepository repo = mock(ContractRepository.class);
        BlockchainContractAdapter adapter = mock(BlockchainContractAdapter.class);
        List<Contract> contracts = Arrays.asList(new Contract(), new Contract());
        when(repo.findAll()).thenReturn(contracts);

        ContractQueryServiceImpl service = new ContractQueryServiceImpl(repo, adapter);
        List<Contract> result = service.handleGetAllContracts();
        assertEquals(2, result.size());
    }
}