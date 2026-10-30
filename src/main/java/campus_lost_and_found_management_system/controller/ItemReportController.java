
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

    // D1: Save an item report
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ItemReport itemReport) {

        String message = itemReportService.save(itemReport);

        if (message.equals("Item report date cannot be in the past")) {
            return ResponseEntity.badRequest().body(message);
        } else if (message.equals("Custodian already handles an item report on that date")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        }
    }

    // Get all item reports
    @GetMapping("/all")
    public ResponseEntity<List<ItemReport>> findAll() {
        return ResponseEntity.ok(itemReportService.findAll());
    }

    // Get an item report by ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ItemReport>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(itemReportService.findById(id));
    }

    // Delete an item report
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {

        String message = itemReportService.delete(id);

        if (message.equals("The item report with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else if (message.equals("That item report has return receipts, delete them first")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
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
            return ResponseEntity.badRequest().body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }

    // B2: Find item reports by status
    @GetMapping("/by-status")
    public ResponseEntity<List<ItemReport>> findByStatus(
            @RequestParam ItemReportStatus status) {

        return ResponseEntity.ok(itemReportService.findByStatus(status));
    }

    // B3: Find item reports between two dates
    @GetMapping("/between")
    public ResponseEntity<List<ItemReport>> findByDateBetween(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {

        return ResponseEntity.ok(
                itemReportService.findByDateBetween(start, end));
    }
      // F1: Get the number of item reports grouped by status
    @GetMapping("/stats/by-status")
    public ResponseEntity<List<Object[]>> countItemReportsByStatus() {
        return ResponseEntity.ok(
                itemReportService.countItemReportsByStatus());
    }

    // F2: Cancel reports for a custodian on a specific date
    @PatchMapping("/cancel-day")
    public ResponseEntity<String> cancelReportsForCustodianOnDate(
            @RequestParam UUID custodianId,
            @RequestParam LocalDate date) {

        String message = itemReportService.cancelReportsForCustodianOnDate(
                custodianId, date);

        return ResponseEntity.ok(message);
    }
}