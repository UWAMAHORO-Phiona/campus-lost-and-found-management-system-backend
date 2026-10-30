package campus_lost_and_found_management_system.repository;

import campus_lost_and_found_management_system.entity.ReturnReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReturnReceiptRepository extends JpaRepository<ReturnReceipt, UUID> {

    @Query("SELECT r FROM ReturnReceipt r WHERE r.itemReport.reporter.campusId = :campusId ORDER BY r.issuedDate DESC")
    List<ReturnReceipt> findByReporterCampusId(@Param("campusId") String campusId);

    boolean existsByItemReport_Id(UUID itemReportId);
}