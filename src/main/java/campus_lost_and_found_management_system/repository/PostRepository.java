package campus_lost_and_found_management_system.repository;

import campus_lost_and_found_management_system.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
}

