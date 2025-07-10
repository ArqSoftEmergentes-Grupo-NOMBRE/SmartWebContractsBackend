// ContractRequestDTO.java
package com.smart.smartwebcontracts.contracts.interfaces.rest.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractRequestDTO {
    private UUID clientId;
    private UUID developerId;
    private UUID webServiceId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal precioTotal;
    private String contractExplorerUrl;
    private List<ContractDeliverableDTO> entregables; // opcional
}
