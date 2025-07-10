// ContractDeliverableDTO.java
package com.smart.smartwebcontracts.contracts.interfaces.rest.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractDeliverableDTO {
    private String titulo;
    private String descripcion;
    private LocalDate fechaEntregaEsperada;
    private BigDecimal precio;
}
