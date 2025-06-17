package com.smart.smartwebcontracts.deliverables.interfaces.rest;

import com.smart.smartwebcontracts.deliverables.application.service.DeliverableCommandServiceImpl;
import com.smart.smartwebcontracts.deliverables.application.service.DeliverableQueryServiceImpl;
import com.smart.smartwebcontracts.deliverables.domain.model.Deliverable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/deliverables")
@RequiredArgsConstructor
public class DeliverableController {

    private final DeliverableCommandServiceImpl deliverableCommandService;
    private final DeliverableQueryServiceImpl deliverableQueryService;

    @PostMapping
    public ResponseEntity<Deliverable> createDeliverable(
            @RequestParam String name,
            @RequestParam LocalDate limit_date,
            @RequestParam String description,
            @RequestParam float price,
            @RequestParam UUID contract_id) {
        Deliverable deliverable = deliverableCommandService.handleCreateDeliverable(name,
                limit_date, description, price, contract_id);
        return ResponseEntity.ok(deliverable);
    }

    @PostMapping("/{id}/to-review")
    public ResponseEntity<Deliverable> setDeliverableToReview(@PathVariable UUID id) {
        Deliverable deliverable = deliverableCommandService.handleSetDeliverableToReview(id);
        return ResponseEntity.ok(deliverable);
    }

    @PostMapping("/{id}/request-changes")
    public ResponseEntity<Deliverable> requestChanges(@PathVariable UUID id) {
        Deliverable deliverable = deliverableCommandService.handleRequestChanges(id);
        return ResponseEntity.ok(deliverable);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Deliverable> approveDeliverable(@PathVariable UUID id) {
        Deliverable deliverable = deliverableCommandService.handleApproveDeliverable(id);
        return ResponseEntity.ok(deliverable);
    }

    @GetMapping
    public ResponseEntity<List<Deliverable>> listDeliverables() {
        List<Deliverable> deliverables = deliverableQueryService.findAll();
        return ResponseEntity.ok(deliverables);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deliverable> getDeliverableById(@PathVariable UUID id) {
        Deliverable deliverable = deliverableQueryService.handleGetDeliverableById(id);
        return ResponseEntity.ok(deliverable);
    }

    @GetMapping("/contract-id/{contract_id}")
    public ResponseEntity<List<Deliverable>> getDeliverablesByContractId(@PathVariable UUID contract_id) {
        List<Deliverable> deliverables = deliverableQueryService.handleGetDeliverablesByContractId(contract_id);
        return ResponseEntity.ok(deliverables);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Deliverable>> getDeliverablesByStatus(@PathVariable String status) {
        List<Deliverable> deliverables = deliverableQueryService.handleGetDeliverablesByStatus(status);
        return ResponseEntity.ok(deliverables);
    }
}
