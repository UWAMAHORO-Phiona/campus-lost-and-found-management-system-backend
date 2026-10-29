
package campus_lost_and_found_management_system.service;

import campus_lost_and_found_management_system.entity.Custodian;
import campus_lost_and_found_management_system.repository.CustodianRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustodianService {

    private final CustodianRepository custodianRepository;

    public CustodianService(CustodianRepository custodianRepository) {
        this.custodianRepository = custodianRepository;
    }

    public Custodian save(Custodian custodian) {
        return custodianRepository.save(custodian);
    }

    public List<Custodian> findAll() {
        return custodianRepository.findAll();
    }

    public Optional<Custodian> findById(UUID id) {
        return custodianRepository.findById(id);
    }

    public String delete(UUID id) {
        if (custodianRepository.existsById(id)) {
            custodianRepository.deleteById(id);
            return "Custodian deleted successfully";
        }
        return "The custodian with that id does not exist";
    }
}
