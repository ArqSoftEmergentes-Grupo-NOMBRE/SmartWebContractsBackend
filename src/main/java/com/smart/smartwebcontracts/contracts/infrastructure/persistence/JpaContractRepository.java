package com.smart.smartwebcontracts.contracts.infrastructure.persistence;

import com.smart.smartwebcontracts.contracts.domain.model.Contract;
import com.smart.smartwebcontracts.contracts.domain.repository.ContractRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JpaContractRepository extends ContractRepository, JpaRepository<Contract, UUID> {


}
