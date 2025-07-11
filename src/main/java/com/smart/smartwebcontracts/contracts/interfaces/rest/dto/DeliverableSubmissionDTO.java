// DeliverableSubmissionDTO.java
package com.smart.smartwebcontracts.contracts.interfaces.rest.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliverableSubmissionDTO {
    private String archivoEntregadoURL;
    private String descripcion;
    private String estado; // opcional: "ENTREGADO"
}
