// ContractControllerTest.java
package com.smart.smartwebcontracts.contracts.interfaces.rest;

import com.smart.smartwebcontracts.contracts.application.service.ContractCommandServiceImpl;
import com.smart.smartwebcontracts.contracts.application.service.ContractQueryServiceImpl;
import com.smart.smartwebcontracts.contracts.domain.model.Contract;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ContractControllerTest {

    @Test
    void updateContractStatus_returnsUpdatedContract() {
        ContractCommandServiceImpl commandService = mock(ContractCommandServiceImpl.class);
        ContractQueryServiceImpl queryService = mock(ContractQueryServiceImpl.class);
        ContractController controller = new ContractController(commandService, queryService);

        UUID id = UUID.randomUUID();
        Contract contract = new Contract();
        contract.setStatus("ACTIVO");
        when(commandService.updateContractStatus(id, "ACTIVO")).thenReturn(contract);

        ResponseEntity<Contract> response = controller.updateContractStatus(id, "ACTIVO");
        assertEquals("ACTIVO", response.getBody().getStatus());
    }
}