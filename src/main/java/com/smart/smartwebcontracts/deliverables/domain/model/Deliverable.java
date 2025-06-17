package com.smart.smartwebcontracts.deliverables.domain.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "deliverables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deliverable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate limit_date;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private String status;

    // Relations
    @Column(name = "contract_id", nullable = false)
    private UUID contractId;

    // Methods
    public void SetDeliverableToReview() { this.status = "To Review"; }
    public void RequestChanges() { this.status = "Changes Required"; }
    public void ApproveDeliverable() { this.status = "Approved"; }
}
