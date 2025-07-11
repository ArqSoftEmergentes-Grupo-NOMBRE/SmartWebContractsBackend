package com.smart.smartwebcontracts.contracts.application.service;

import com.smart.smartwebcontracts.contracts.domain.model.Contract;
import com.smart.smartwebcontracts.contracts.domain.repository.ContractRepository;
import com.smart.smartwebcontracts.contracts.infrastructure.blockchain.BlockchainContractAdapter;
import com.smart.smartwebcontracts.contracts.interfaces.rest.dto.ContractRequestDTO;
import com.smart.smartwebcontracts.contracts.domain.model.ContractDeliverable;
import com.smart.smartwebcontracts.contracts.domain.model.DeliverableStatus;

import com.smart.smartwebcontracts.contracts.interfaces.rest.dto.DeliverableSubmissionDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContractCommandServiceImpl {

    private final ContractRepository contractRepository;
    private final BlockchainContractAdapter blockchainContractAdapter; // <- nuevo


    public Contract handleCreateContract(ContractRequestDTO dto) {
        Contract newContract = Contract.builder()
                .clientId(dto.getClientId())
                .developerId(dto.getDeveloperId())
                .webServiceId(dto.getWebServiceId())
                .fechaInicio(dto.getFechaInicio() != null ? dto.getFechaInicio() : LocalDate.now())
                .fechaFin(dto.getFechaFin())
                .precioTotal(dto.getPrecioTotal())
                .contractExplorerUrl(null)  // se define luego del registro en blockchain
                .status("CREADO")
                .entregables(new ArrayList<>())
                .build();

        final Contract contractFinal = newContract;

        if (dto.getEntregables() != null) {
            dto.getEntregables().forEach(ent -> {
                contractFinal.getEntregables().add(
                        ContractDeliverable.builder()
                                .contract(contractFinal)
                                .titulo(ent.getTitulo())
                                .descripcion(ent.getDescripcion())
                                .fechaEntregaEsperada(ent.getFechaEntregaEsperada())
                                .estado(DeliverableStatus.PENDIENTE)
                                .precio(ent.getPrecio())
                                .build()
                );
            });
        }

        Contract saved = contractRepository.save(contractFinal);

        String txHash = blockchainContractAdapter.registerFullContractOnBlockchain(saved);
        saved.setSmartContractHash(txHash);
        saved.setContractExplorerUrl("https://sepolia.etherscan.io/tx/" + txHash);

        return contractRepository.save(saved);
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

    public Contract updateContractStatus(UUID contractId, String nuevoEstado) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new IllegalArgumentException("Contrato no encontrado"));

        contract.setStatus(nuevoEstado);
        return contractRepository.save(contract);
    }

    public ContractDeliverable handleDeliverableSubmission(UUID contractId, UUID deliverableId, DeliverableSubmissionDTO dto) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new IllegalArgumentException("Contrato no encontrado"));

        ContractDeliverable deliverable = contract.getEntregables().stream()
                .filter(e -> e.getId().equals(deliverableId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Entregable no encontrado"));

        deliverable.setArchivoEntregadoURL(dto.getArchivoEntregadoURL());
        deliverable.setDescripcion(dto.getDescripcion());

        if (dto.getEstado() != null && !dto.getEstado().isEmpty()) {
            deliverable.setEstado(DeliverableStatus.valueOf(dto.getEstado()));
        } else {
            deliverable.setEstado(DeliverableStatus.ENTREGADO);
        }

        // Verificar si todos los entregables están ENTREGADOS
        boolean todosEntregados = contract.getEntregables().stream()
                .allMatch(e -> e.getEstado() == DeliverableStatus.ENTREGADO);

        if (todosEntregados) {
            contract.setStatus("ENVIADO");  // O el nombre exacto que estés usando
        }

        contractRepository.save(contract);
        return deliverable;
    }

}
