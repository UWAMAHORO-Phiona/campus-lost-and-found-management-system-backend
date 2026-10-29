
package campus_lost_and_found_management_system.controller;

import campus_lost_and_found_management_system.entity.ItemCategory;
import campus_lost_and_found_management_system.service.ItemCategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/item-categories")
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;

    public ItemCategoryController(ItemCategoryService itemCategoryService) {
        this.itemCategoryService = itemCategoryService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody ItemCategory itemCategory) {
        itemCategoryService.save(itemCategory);
        return new ResponseEntity<>(
                "Item category saved successfully",
                HttpStatus.CREATED
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemCategory>> findAll() {
        return ResponseEntity.ok(itemCategoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        Optional<ItemCategory> itemCategory = itemCategoryService.findById(id);

        if (itemCategory.isPresent()) {
            return ResponseEntity.ok(itemCategory.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The item category with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody ItemCategory updatedCategory) {

        Optional<ItemCategory> existingCategory = itemCategoryService.findById(id);

        if (existingCategory.isPresent()) {
            ItemCategory category = existingCategory.get();
            category.setName(updatedCategory.getName());
            itemCategoryService.save(category);

            return ResponseEntity.ok("Item category updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The item category with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = itemCategoryService.delete(id);

        if (message.equals("The item category with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        } else {
            return ResponseEntity.ok(message);
        }
    }
}
