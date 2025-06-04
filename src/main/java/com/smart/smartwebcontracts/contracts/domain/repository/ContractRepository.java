package com.smart.smartwebcontracts.contracts.domain.repository;

import com.smart.smartwebcontracts.contracts.domain.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, UUID> {

    List<Contract> findByClientId(UUID clientId);

    List<Contract> findByDeveloperId(UUID developerId);

    List<Contract> findByStatus(String status);
}
