
package campus_lost_and_found_management_system.repository;

import campus_lost_and_found_management_system.entity.ItemReport;
import campus_lost_and_found_management_system.entity.ItemReportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;
import java.time.LocalDate;

public interface ItemReportRepository extends JpaRepository<ItemReport, UUID> {

    // B2: Find reports by status
    List<ItemReport> findByStatusOrderByReportDateAsc(ItemReportStatus status);

    // B3: Find reports between two dates
    List<ItemReport> findByReportDateBetweenOrderByReportDateAsc(
            LocalDate startDate, LocalDate endDate);

    // D1: Check if a custodian already has a report on that date
    boolean existsByCustodian_IdAndReportDateAndStatusNot(
            UUID custodianId,
            LocalDate reportDate,
            ItemReportStatus status
    );

    // F1: Count reports grouped by status
    @Query("SELECT r.status, COUNT(r) FROM ItemReport r GROUP BY r.status")
    List<Object[]> countItemReportsByStatus();

    // F2: Cancel reports for a custodian on a specific date
    @Modifying
    @Transactional
    @Query("UPDATE ItemReport r SET r.status = :newStatus " +
           "WHERE r.custodian.id = :custodianId " +
           "AND r.reportDate = :date " +
           "AND r.status <> :excludedStatus")
    int cancelReportsForCustodianOnDate(
            @Param("custodianId") UUID custodianId,
            @Param("date") LocalDate date,
            @Param("newStatus") ItemReportStatus newStatus,
            @Param("excludedStatus") ItemReportStatus excludedStatus
    );

    // Dashboard: Count reports created on a specific date
long countByReportDate(LocalDate reportDate);

// Dashboard: Count reports by status
long countByStatus(ItemReportStatus status);

Page<ItemReport> findAll(Pageable pageable);
}
