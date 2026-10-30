package campus_lost_and_found_management_system.repository;

import campus_lost_and_found_management_system.entity.ItemReport;
import campus_lost_and_found_management_system.entity.ItemReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

public interface ItemReportRepository extends JpaRepository<ItemReport, UUID> {

    // B2
    List<ItemReport> findByStatusOrderByReportDateAsc(ItemReportStatus status);

    // B3
    List<ItemReport> findByReportDateBetweenOrderByReportDateAsc(
            LocalDate startDate, LocalDate endDate);

    // D1
    boolean existsByCustodian_IdAndReportDateAndStatusNot(
            UUID custodianId,
            LocalDate reportDate,
            ItemReportStatus status
    );
}