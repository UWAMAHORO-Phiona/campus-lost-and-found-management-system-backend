
package campus_lost_and_found_management_system.service;

import campus_lost_and_found_management_system.entity.ItemReport;
import campus_lost_and_found_management_system.repository.ItemReportRepository;
import campus_lost_and_found_management_system.entity.ItemReportStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;

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
    public List<ItemReport> findByStatus(ItemReportStatus status) {
        return itemReportRepository.findByStatusOrderByReportDateAsc(status);
    }
    public List<ItemReport> findByDateBetween(LocalDate startDate, LocalDate endDate) {
        return itemReportRepository.findByReportDateBetweenOrderByReportDateAsc(startDate, endDate);
    }
}
