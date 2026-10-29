
package campus_lost_and_found_management_system.repository;

import campus_lost_and_found_management_system.entity.Reporter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ReporterRepository extends JpaRepository<Reporter, UUID> {
}
