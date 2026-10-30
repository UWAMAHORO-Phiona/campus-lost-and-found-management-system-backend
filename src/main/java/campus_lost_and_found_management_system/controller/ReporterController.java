
package campus_lost_and_found_management_system.controller;

import campus_lost_and_found_management_system.entity.Reporter;
import campus_lost_and_found_management_system.service.ReporterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/reporters")
public class ReporterController {

    private final ReporterService reporterService;

    public ReporterController(ReporterService reporterService) {
        this.reporterService = reporterService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Reporter reporter) {
        reporterService.save(reporter);
        return new ResponseEntity<>(
                "Reporter saved successfully",
                HttpStatus.CREATED
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reporter>> findAll() {
        return ResponseEntity.ok(reporterService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Reporter> reporter = reporterService.findById(id);

        if (reporter.isPresent()) {
            return ResponseEntity.ok(reporter.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The reporter with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody Reporter updatedReporter) {

        Optional<Reporter> existingReporter =
                reporterService.findById(id);

        if (existingReporter.isPresent()) {
            Reporter reporter = existingReporter.get();
            reporter.setFirstName(updatedReporter.getFirstName());
            reporter.setLastName(updatedReporter.getLastName());
            reporter.setCampusId(updatedReporter.getCampusId());
            reporter.setEmail(updatedReporter.getEmail());
            reporter.setPhone(updatedReporter.getPhone());

            reporterService.save(reporter);

            return ResponseEntity.ok("Reporter updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The reporter with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = reporterService.delete(id);

        if (message.equals("The reporter with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }
    @GetMapping("/by-last-name")
    public ResponseEntity<List<Reporter>> findByLastName(
        @RequestParam String lastName) {

            return ResponseEntity.ok(
                reporterService.findByLastName(lastName)
            );
    
        }
    }