
package campus_lost_and_found_management_system.repository;

import campus_lost_and_found_management_system.entity.Reporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReporterRepository extends JpaRepository<Reporter, UUID> {
    List<Reporter> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

@Query("SELECT DISTINCT ir.reporter FROM ItemReport ir WHERE ir.custodian.id = :custodianId")
List<Reporter> findReportersByCustodian(@Param("custodianId") UUID custodianId);

long countBy();
}

