package campus_lost_and_found_management_system.controller;

import campus_lost_and_found_management_system.entity.ItemReport;
import campus_lost_and_found_management_system.entity.ItemReportStatus;
import campus_lost_and_found_management_system.service.ItemReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/item-reports")
public class ItemReportController {

    private final ItemReportService itemReportService;

    public ItemReportController(ItemReportService itemReportService) {
        this.itemReportService = itemReportService;
    }

    // D1: Safe saving
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ItemReport itemReport) {

        String message = itemReportService.save(itemReport);

        if (message.equals("Item report date cannot be in the past")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);

        } else if (message.equals(
                "Custodian already handles an item report on that date")) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);

        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemReport>> findAll() {
        return ResponseEntity.ok(itemReportService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {

        Optional<ItemReport> itemReport = itemReportService.findById(id);

        if (itemReport.isPresent()) {
            return ResponseEntity.ok(itemReport.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The item report with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody ItemReport updatedItemReport) {

        Optional<ItemReport> existingItemReport =
                itemReportService.findById(id);

        if (existingItemReport.isPresent()) {

            ItemReport itemReport = existingItemReport.get();

            itemReport.setReportDate(updatedItemReport.getReportDate());
            itemReport.setItemName(updatedItemReport.getItemName());
            itemReport.setLocation(updatedItemReport.getLocation());
            itemReport.setReporter(updatedItemReport.getReporter());
            itemReport.setCustodian(updatedItemReport.getCustodian());

            itemReportService.update(itemReport);

            return ResponseEntity.ok("Item report updated successfully");

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The item report with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {

        String message = itemReportService.delete(id);

        if (message.equals("The item report with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }
// D2: Update item report status
@PatchMapping("/{id}/status")
public ResponseEntity<String> updateStatus(
        @PathVariable UUID id,
        @RequestParam ItemReportStatus status) {

    String message = itemReportService.updateStatus(id, status);

    if (message.equals("The item report with that id does not exist")) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);

    } else if (message.startsWith("Cannot change status")) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);

    } else {
        return ResponseEntity.ok(message);
    }
}
    // B2
    @GetMapping("/by-status")
    public ResponseEntity<List<ItemReport>> findByStatus(
            @RequestParam ItemReportStatus status) {

        return ResponseEntity.ok(itemReportService.findByStatus(status));
    }

    // B3
    @GetMapping("/between")
    public ResponseEntity<List<ItemReport>> findByDateBetween(
            @RequestParam String start,
            @RequestParam String end) {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        return ResponseEntity.ok(
                itemReportService.findByDateBetween(startDate, endDate));
    }
}