
package campus_lost_and_found_management_system.service;

import campus_lost_and_found_management_system.entity.Reporter;
import campus_lost_and_found_management_system.repository.ReporterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReporterService {

    private final ReporterRepository reporterRepository;

    public ReporterService(ReporterRepository reporterRepository) {
        this.reporterRepository = reporterRepository;
    }

    public Reporter save(Reporter reporter) {
        return reporterRepository.save(reporter);
    }

    public List<Reporter> findAll() {
        return reporterRepository.findAll();
    }

    public Optional<Reporter> findById(UUID id) {
        return reporterRepository.findById(id);
    }

    public String delete(UUID id) {
        if (reporterRepository.existsById(id)) {
            reporterRepository.deleteById(id);
            return "Reporter deleted successfully";
        }
        return "The reporter with that id does not exist";
    }
    public List<Reporter> findByLastName(String lastName) {
        return reporterRepository.findByLastNameIgnoreCaseOrderByFirstNameAsc(lastName);
    }
    public List<Reporter> findReportersByCustodian(UUID custodianId) {
    return reporterRepository.findReportersByCustodian(custodianId);
}
}
