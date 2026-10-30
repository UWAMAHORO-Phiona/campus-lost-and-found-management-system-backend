
package campus_lost_and_found_management_system.service;

import campus_lost_and_found_management_system.entity.Post;
import campus_lost_and_found_management_system.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Optional<Post> findById(UUID id) {
        return postRepository.findById(id);
    }

    public String delete(UUID id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
            return "Post deleted successfully";
        }
        return "The post with that id does not exist";
    }
    public List<Post> findFreePosts() {
        return postRepository.findFreePosts();
    }
}
