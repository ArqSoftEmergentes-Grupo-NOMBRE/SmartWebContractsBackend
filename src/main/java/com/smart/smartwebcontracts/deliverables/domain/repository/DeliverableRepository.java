package com.smart.smartwebcontracts.deliverables.domain.repository;

import com.smart.smartwebcontracts.deliverables.domain.model.Deliverable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DeliverableRepository extends JpaRepository<Deliverable, UUID> {
    List<Deliverable> findByContractId(UUID contractId);
    List<Deliverable> findByStatus(String status);
}
