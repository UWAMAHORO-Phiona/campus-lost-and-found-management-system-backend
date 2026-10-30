package campus_lost_and_found_management_system.controller;

import campus_lost_and_found_management_system.entity.ReturnReceipt;
import campus_lost_and_found_management_system.service.ReturnReceiptService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/return-receipts")
public class ReturnReceiptController {

    private final ReturnReceiptService returnReceiptService;

    public ReturnReceiptController(ReturnReceiptService returnReceiptService) {
        this.returnReceiptService = returnReceiptService;
    }

    // E1: Save return receipt
    @PostMapping("/save")
    public ResponseEntity<String> save(
            @RequestBody ReturnReceipt receipt,
            @RequestParam UUID itemReportId) {

        String message = returnReceiptService.save(receipt, itemReportId);

        if (message.equals("The item report with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);

        } else if (message.equals(
                "Return receipts are only for RETURNED item reports")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);

        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
    }

    // E2: Find receipts by reporter campus ID
    @GetMapping("/reporter/{campusId}")
    public ResponseEntity<List<ReturnReceipt>> findByReporter(
            @PathVariable String campusId) {

        return ResponseEntity.ok(
                returnReceiptService.findByReporterCampusId(campusId));
    }

    // E3: Delete return receipt
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {

        String message = returnReceiptService.delete(id);

        if (message.equals("The return receipt with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }
}