package campus_lost_and_found_management_system.service;

import campus_lost_and_found_management_system.entity.Custodian;
import campus_lost_and_found_management_system.entity.ItemCategory;
import campus_lost_and_found_management_system.entity.Post;
import campus_lost_and_found_management_system.repository.CustodianRepository;
import campus_lost_and_found_management_system.repository.ItemCategoryRepository;
import campus_lost_and_found_management_system.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustodianService {

    private final CustodianRepository custodianRepository;
    private final ItemCategoryRepository itemCategoryRepository;
    private final PostRepository postRepository;

    public CustodianService(
            CustodianRepository custodianRepository,
            ItemCategoryRepository itemCategoryRepository,
            PostRepository postRepository) {

        this.custodianRepository = custodianRepository;
        this.itemCategoryRepository = itemCategoryRepository;
        this.postRepository = postRepository;
    }

    public Custodian save(Custodian custodian) {
        return custodianRepository.save(custodian);
    }

    public List<Custodian> findAll() {
        return custodianRepository.findAll();
    }

    public Optional<Custodian> findById(UUID id) {
        return custodianRepository.findById(id);
    }

    public String delete(UUID id) {
        if (custodianRepository.existsById(id)) {
            custodianRepository.deleteById(id);
            return "Custodian deleted successfully";
        }

        return "The custodian with that id does not exist";
    }

    public List<Custodian> findByCategory(String categoryName) {
        return custodianRepository.findCustodiansByCategory(categoryName);
    }

    // D3: Add an item category to a custodian
    public String addItemCategory(UUID custodianId, UUID itemCategoryId) {

        Optional<Custodian> optionalCustodian =
                custodianRepository.findById(custodianId);

        if (optionalCustodian.isEmpty()) {
            return "The custodian with that id does not exist";
        }

        Optional<ItemCategory> optionalCategory =
                itemCategoryRepository.findById(itemCategoryId);

        if (optionalCategory.isEmpty()) {
            return "That item category does not exist";
        }

        Custodian custodian = optionalCustodian.get();
        ItemCategory category = optionalCategory.get();

        if (custodian.getItemCategories().contains(category)) {
            return "The custodian already has that item category";
        }

        custodian.getItemCategories().add(category);
        custodianRepository.save(custodian);

        return "Item category added to the custodian";
    }

    // D4: Change the post assigned to a custodian
    public String changePost(UUID custodianId, int postNumber) {

        Optional<Custodian> optionalCustodian =
                custodianRepository.findById(custodianId);

        if (optionalCustodian.isEmpty()) {
            return "The custodian with that id does not exist";
        }

        Optional<Post> optionalPost =
                postRepository.findByPostNumber(postNumber);

        if (optionalPost.isEmpty()) {
            return "The post with that postNumber does not exist";
        }

        Custodian custodian = optionalCustodian.get();
        Post post = optionalPost.get();

        if (custodianRepository.existsByPost_IdAndIdNot(
                post.getId(), custodianId)) {
            return "That post is taken by another custodian";
        }

        custodian.setPost(post);
        custodianRepository.save(custodian);

        return "Post changed successfully";
    }
}