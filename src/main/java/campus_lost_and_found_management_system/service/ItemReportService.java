
package campus_lost_and_found_management_system.service;

import campus_lost_and_found_management_system.entity.ItemReport;
import campus_lost_and_found_management_system.repository.ItemReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemReportService {

    private final ItemReportRepository itemReportRepository;

    public ItemReportService(ItemReportRepository itemReportRepository) {
        this.itemReportRepository = itemReportRepository;
    }

    public ItemReport save(ItemReport itemReport) {
        return itemReportRepository.save(itemReport);
    }

    public List<ItemReport> findAll() {
        return itemReportRepository.findAll();
    }

    public Optional<ItemReport> findById(UUID id) {
        return itemReportRepository.findById(id);
    }

    public String delete(UUID id) {
        if (itemReportRepository.existsById(id)) {
            itemReportRepository.deleteById(id);
            return "Item report deleted successfully";
        }
        return "The item report with that id does not exist";
    }
}
