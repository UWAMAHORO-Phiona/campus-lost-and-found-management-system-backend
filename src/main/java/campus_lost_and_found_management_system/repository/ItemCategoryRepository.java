package campus_lost_and_found_management_system.repository;

import campus_lost_and_found_management_system.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, UUID> {
    @Query("SELECT ic FROM ItemCategory ic WHERE ic.custodians IS EMPTY")
    List<ItemCategory> findUnusedCategories();
}