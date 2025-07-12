// ContractCommandServiceImplTest.java
package com.smart.smartwebcontracts.contracts.application.service;

import com.smart.smartwebcontracts.contracts.domain.model.Contract;
import com.smart.smartwebcontracts.contracts.domain.model.ContractDeliverable;
import com.smart.smartwebcontracts.contracts.domain.model.DeliverableStatus;
import com.smart.smartwebcontracts.contracts.domain.repository.ContractRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContractCommandServiceImplTest {

    @Test
    void updateContractStatus_validStatus_updatesStatus() {
        ContractRepository repo = mock(ContractRepository.class);
        Contract contract = new Contract();
        UUID id = UUID.randomUUID();
        when(repo.findById(id)).thenReturn(Optional.of(contract));
        when(repo.save(any())).thenReturn(contract);

        ContractCommandServiceImpl service = new ContractCommandServiceImpl(repo, null);
        Contract updated = service.updateContractStatus(id, "ACTIVO");
        assertEquals("ACTIVO", updated.getStatus());
    }

    @Test
    void updateDeliverableStatus_invalidStatus_throwsException() {
        ContractRepository repo = mock(ContractRepository.class);
        Contract contract = new Contract();
        ContractDeliverable deliverable = new ContractDeliverable();
        deliverable.setId(UUID.randomUUID());
        contract.getEntregables().add(deliverable);
        UUID contractId = UUID.randomUUID();
        UUID deliverableId = deliverable.getId();
        when(repo.findById(contractId)).thenReturn(Optional.of(contract));

        ContractCommandServiceImpl service = new ContractCommandServiceImpl(repo, null);
        assertThrows(IllegalArgumentException.class, () ->
                service.updateDeliverableStatus(contractId, deliverableId, "INVALID_STATUS")
        );
    }
}