
package campus_lost_and_found_management_system.repository;

import campus_lost_and_found_management_system.entity.ItemReport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ItemReportRepository extends JpaRepository<ItemReport, UUID> {
}
