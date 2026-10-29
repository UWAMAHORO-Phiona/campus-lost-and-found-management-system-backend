
package campus_lost_and_found_management_system.controller;

import campus_lost_and_found_management_system.entity.Post;
import campus_lost_and_found_management_system.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Post post) {
        postService.save(post);
        return new ResponseEntity<>(
                "Post saved successfully",
                HttpStatus.CREATED
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<Post>> findAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<Post> post = postService.findById(id);

        if (post.isPresent()) {
            return ResponseEntity.ok(post.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The post with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody Post updatedPost) {

        Optional<Post> existingPost = postService.findById(id);

        if (existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setPostNumber(updatedPost.getPostNumber());
            post.setName(updatedPost.getName());
            postService.save(post);

            return ResponseEntity.ok("Post updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The post with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = postService.delete(id);

        if (message.equals("The post with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }
}
