package com.smart.smartwebcontracts.deliverables.application.service;

import com.smart.smartwebcontracts.deliverables.domain.model.Deliverable;
import com.smart.smartwebcontracts.deliverables.domain.repository.DeliverableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliverableQueryServiceImpl {

    private final DeliverableRepository deliverableRepository;

    public Deliverable handleGetDeliverableById(UUID deliverableId) {
        return deliverableRepository.findById(deliverableId)
                .orElseThrow(() -> new IllegalArgumentException("Contract not found"));
    }

    public List<Deliverable> handleGetDeliverablesByContractId(UUID contractId) {
        return deliverableRepository.findByContractId(contractId);
    }

    public List<Deliverable> handleGetDeliverablesByStatus(String status) {
        return deliverableRepository.findByStatus(status);
    }

    public List<Deliverable> findAll() { return deliverableRepository.findAll(); }
}
