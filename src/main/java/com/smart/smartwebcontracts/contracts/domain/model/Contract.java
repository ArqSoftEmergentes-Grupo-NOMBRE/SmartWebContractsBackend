package com.smart.smartwebcontracts.contracts.domain.model;

import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "contracts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private String status;

    @Column(name = "smart_contract_hash")
    private String smartContractHash;

    // Relaciones
    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "developer_id", nullable = false)
    private UUID developerId;

    @Column(name = "web_service_id", nullable = false)
    private UUID webServiceId;

    // Métodos de dominio (ejemplos)
    public void signContract() {
        this.status = "EN_PROCESO";
    }

    public void finalizeContract() {
        this.status = "COMPLETADO";
    }
}
