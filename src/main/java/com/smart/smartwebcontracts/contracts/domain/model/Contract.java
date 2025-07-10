package com.smart.smartwebcontracts.contracts.domain.model;

import lombok.*;
import jakarta.persistence.*;

import java.math.BigDecimal;
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
    private String status;

    @Column(name = "smart_contract_hash")
    private String smartContractHash;

    // 🆕 Nuevos campos
    @Column(name = "start_date", nullable = false)
    private LocalDate fechaInicio = LocalDate.now();


    private LocalDate fechaFin;

    @Column(precision = 10, scale = 2)
    private BigDecimal precioTotal;

    @Column(name = "contract_explorer_url")
    private String contractExplorerUrl;

    // Relaciones
    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @Column(name = "developer_id", nullable = false)
    private UUID developerId;

    @Column(name = "web_service_id", nullable = false)
    private UUID webServiceId;

    // Métodos de dominio
    public void signContract() {
        this.status = "EN_PROCESO";
    }

    public void finalizeContract() {
        this.status = "COMPLETADO";
        this.fechaFin = LocalDate.now();
    }

    // Relación con entregables
    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContractDeliverable> entregables = new ArrayList<>();
}