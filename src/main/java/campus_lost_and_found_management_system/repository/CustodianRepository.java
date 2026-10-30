package campus_lost_and_found_management_system.repository;

import campus_lost_and_found_management_system.entity.Custodian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CustodianRepository extends JpaRepository<Custodian, UUID> {

    // C1: Find custodians by item category
    @Query("SELECT c FROM Custodian c JOIN c.itemCategories ic WHERE LOWER(ic.name) = LOWER(:categoryName)")
    List<Custodian> findCustodiansByCategory(@Param("categoryName") String categoryName);

    // D4: Check whether another custodian is assigned to the post
    boolean existsByPost_IdAndIdNot(UUID postId, UUID custodianId);
}