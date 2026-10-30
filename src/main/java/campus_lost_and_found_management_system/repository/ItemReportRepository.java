
package campus_lost_and_found_management_system.repository;

import campus_lost_and_found_management_system.entity.ItemReport;
import campus_lost_and_found_management_system.entity.ItemReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

public interface ItemReportRepository extends JpaRepository<ItemReport, UUID> {
    List<ItemReport> findByStatusOrderByReportDateAsc(ItemReportStatus status);
    List<ItemReport> findByReportDateBetweenOrderByReportDateAsc(LocalDate startDate, LocalDate endDate);
}
