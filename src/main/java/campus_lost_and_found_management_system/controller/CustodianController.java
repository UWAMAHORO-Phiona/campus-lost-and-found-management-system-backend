
package campus_lost_and_found_management_system.controller;

import campus_lost_and_found_management_system.entity.Custodian;
import campus_lost_and_found_management_system.service.CustodianService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/custodians")
public class CustodianController {

    private final CustodianService custodianService;

    public CustodianController(CustodianService custodianService) {
        this.custodianService = custodianService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Custodian custodian) {
        custodianService.save(custodian);
        return new ResponseEntity<>(
                "Custodian saved successfully",
                HttpStatus.CREATED
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<Custodian>> findAll() {
        return ResponseEntity.ok(custodianService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Custodian> custodian = custodianService.findById(id);

        if (custodian.isPresent()) {
            return ResponseEntity.ok(custodian.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The custodian with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody Custodian updatedCustodian) {

        Optional<Custodian> existingCustodian = custodianService.findById(id);

        if (existingCustodian.isPresent()) {
            Custodian custodian = existingCustodian.get();
            custodian.setFirstName(updatedCustodian.getFirstName());
            custodian.setLastName(updatedCustodian.getLastName());
            custodian.setEmail(updatedCustodian.getEmail());
            custodian.setPost(updatedCustodian.getPost());
            custodian.setItemCategories(updatedCustodian.getItemCategories());

            custodianService.save(custodian);

            return ResponseEntity.ok("Custodian updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The custodian with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = custodianService.delete(id);

        if (message.equals("The custodian with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }
}
