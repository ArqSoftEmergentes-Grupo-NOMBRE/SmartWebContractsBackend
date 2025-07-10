package com.smart.smartwebcontracts.contracts.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "contract_deliverables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractDeliverable {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contract_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Contract contract;


    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private LocalDate fechaEntregaEsperada;

    private String archivoEntregadoURL;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliverableStatus estado = DeliverableStatus.PENDIENTE;

    @Column(precision = 10, scale = 2)
    private BigDecimal precio;
}
