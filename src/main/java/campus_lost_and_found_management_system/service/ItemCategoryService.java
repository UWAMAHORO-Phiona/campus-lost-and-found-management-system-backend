
package campus_lost_and_found_management_system.service;

import campus_lost_and_found_management_system.entity.ItemCategory;
import campus_lost_and_found_management_system.repository.ItemCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;

    public ItemCategoryService(ItemCategoryRepository itemCategoryRepository) {
        this.itemCategoryRepository = itemCategoryRepository;
    }

    public ItemCategory save(ItemCategory itemCategory) {
        return itemCategoryRepository.save(itemCategory);
    }

    public List<ItemCategory> findAll() {
        return itemCategoryRepository.findAll();
    }

    public Optional<ItemCategory> findById(UUID id) {
        return itemCategoryRepository.findById(id);
    }

    public String delete(UUID id) {
        if (itemCategoryRepository.existsById(id)) {
            itemCategoryRepository.deleteById(id);
            return "Item category deleted successfully";
        }
        return "The item category with that id does not exist";
    }
}
