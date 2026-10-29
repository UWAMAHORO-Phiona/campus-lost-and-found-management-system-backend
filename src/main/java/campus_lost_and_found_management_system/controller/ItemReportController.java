
package campus_lost_and_found_management_system.controller;

import campus_lost_and_found_management_system.entity.ItemReport;
import campus_lost_and_found_management_system.service.ItemReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/item-reports")
public class ItemReportController {

    private final ItemReportService itemReportService;

    public ItemReportController(ItemReportService itemReportService) {
        this.itemReportService = itemReportService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ItemReport itemReport) {
        itemReportService.save(itemReport);
        return new ResponseEntity<>(
                "Item report saved successfully",
                HttpStatus.CREATED
        );
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
            itemReport.setStatus(updatedItemReport.getStatus());
            itemReport.setItemName(updatedItemReport.getItemName());
            itemReport.setLocation(updatedItemReport.getLocation());
            itemReport.setReporter(updatedItemReport.getReporter());
            itemReport.setCustodian(updatedItemReport.getCustodian());

            itemReportService.save(itemReport);

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
}
