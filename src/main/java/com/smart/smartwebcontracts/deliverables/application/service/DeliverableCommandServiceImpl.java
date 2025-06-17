package com.smart.smartwebcontracts.deliverables.application.service;

import com.smart.smartwebcontracts.deliverables.domain.model.Deliverable;
import com.smart.smartwebcontracts.deliverables.infrastructure.persistence.jpa.DeliverableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliverableCommandServiceImpl {

    private final DeliverableRepository deliverableRepository;

    public Deliverable handleCreateDeliverable(String name, LocalDate limit_date,
                                               String description, float price,
                                               UUID contractId) {
        Deliverable deliverable = Deliverable.builder()
                .id(UUID.randomUUID())
                .name(name)
                .limit_date(limit_date)
                .description(description)
                .price(price)
                .status("To Do")
                .contractId(contractId)
                .build();
        return deliverableRepository.save(deliverable);
    }

    public Deliverable handleSetDeliverableToReview(UUID deliverableId) {
        Deliverable deliverable = deliverableRepository.findById(deliverableId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid deliverable ID"));
        deliverable.SetDeliverableToReview();
        return deliverableRepository.save(deliverable);
    }

    public Deliverable handleRequestChanges(UUID deliverableId) {
        Deliverable deliverable = deliverableRepository.findById(deliverableId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid deliverable ID"));
        deliverable.RequestChanges();
        return deliverableRepository.save(deliverable);
    }

    public Deliverable handleApproveDeliverable(UUID deliverableId) {
        Deliverable deliverable = deliverableRepository.findById(deliverableId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid deliverable ID"));
        deliverable.ApproveDeliverable();
        return deliverableRepository.save(deliverable);
    }
}
