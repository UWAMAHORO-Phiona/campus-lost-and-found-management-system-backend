package campus_lost_and_found_management_system.repository;

import campus_lost_and_found_management_system.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, UUID> {
      @Query("SELECT p FROM Post p WHERE NOT EXISTS (SELECT c FROM Custodian c WHERE c.post = p)")
    List<Post> findFreePosts();

} 


