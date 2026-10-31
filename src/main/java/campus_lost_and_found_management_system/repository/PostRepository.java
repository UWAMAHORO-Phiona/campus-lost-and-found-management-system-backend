package campus_lost_and_found_management_system.repository;

import campus_lost_and_found_management_system.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    // C2: Find posts that have no custodian
   @Query("SELECT p FROM Post p WHERE NOT EXISTS (SELECT c FROM Custodian c WHERE c.post = p) ORDER BY p.postNumber ASC")
    List<Post> findFreePosts();

    // D4: Find a post using its post number
    Optional<Post> findByPostNumber(int postNumber);

    // D4: Check whether a post number exists
    boolean existsByPostNumber(int postNumber);
}