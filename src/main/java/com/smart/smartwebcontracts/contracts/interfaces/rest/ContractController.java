package com.smart.smartwebcontracts.contracts.interfaces.rest;

import com.smart.smartwebcontracts.contracts.application.service.ContractCommandServiceImpl;
import com.smart.smartwebcontracts.contracts.application.service.ContractQueryServiceImpl;
import com.smart.smartwebcontracts.contracts.infrastructure.blockchain.dto.HashRecordDTO;
import com.smart.smartwebcontracts.contracts.domain.model.Contract;
import com.smart.smartwebcontracts.contracts.infrastructure.blockchain.dto.SmartContractDTO;
import com.smart.smartwebcontracts.contracts.interfaces.rest.dto.ContractRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractCommandServiceImpl contractCommandService;
    private final ContractQueryServiceImpl contractQueryService;

    // Crear contrato
// Crear contrato
    @PostMapping
    public ResponseEntity<Contract> createContract(@RequestBody ContractRequestDTO dto) {
        Contract contract = contractCommandService.handleCreateContract(dto);
        return ResponseEntity.ok(contract);
    }


    // Firmar contrato
    @PostMapping("/{id}/sign")
    public ResponseEntity<Contract> signContract(@PathVariable UUID id) {
        Contract contract = contractCommandService.handleSignContract(id);
        return ResponseEntity.ok(contract);
    }

    // Finalizar contrato
    @PostMapping("/{id}/finalize")
    public ResponseEntity<Contract> finalizeContract(@PathVariable UUID id) {
        Contract contract = contractCommandService.handleFinalizeContract(id);
        return ResponseEntity.ok(contract);
    }

    // Obtener contrato por ID
    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContractById(@PathVariable UUID id) {
        Contract contract = contractQueryService.handleGetContractById(id);
        return ResponseEntity.ok(contract);
    }

    // Obtener contratos activos del usuario
    @GetMapping("/active")
    public ResponseEntity<List<Contract>> getActiveContractsByUser(@RequestParam UUID userId) {
        List<Contract> contracts = contractQueryService.handleGetActiveContractsByUser(userId);
        return ResponseEntity.ok(contracts);
    }

    // Obtener todos los hashes registrados en blockchain
    @GetMapping("/blockchain/hashes")
    public ResponseEntity<List<HashRecordDTO>> getAllHashesFromBlockchain() {
        List<HashRecordDTO> hashes = contractQueryService.handleGetAllHashRecords();
        return ResponseEntity.ok(hashes);
    }

    @PostMapping("/blockchain/register")
    public ResponseEntity<String> testRegisterHash(@RequestParam String hash) {
        String txHash = contractCommandService.registerContractHashOnBlockchain(hash);
        return ResponseEntity.ok(txHash);
    }


    @GetMapping("/blockchain/contracts")
    public ResponseEntity<List<SmartContractDTO>> getAllContractsFromBlockchain() {
        List<SmartContractDTO> contracts = contractQueryService.handleGetAllSmartContracts();
        return ResponseEntity.ok(contracts);
    }


    // Obtener todos los contratos
    @GetMapping
    public ResponseEntity<List<Contract>> getAllContracts() {
        List<Contract> contracts = contractQueryService.handleGetAllContracts();
        return ResponseEntity.ok(contracts);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Contract> updateContractStatus(
            @PathVariable UUID id,
            @RequestParam String status
    ) {
        Contract updatedContract = contractCommandService.updateContractStatus(id, status);
        return ResponseEntity.ok(updatedContract);
    }

}
