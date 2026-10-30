package campus_lost_and_found_management_system.service;

import campus_lost_and_found_management_system.entity.ItemReport;
import campus_lost_and_found_management_system.entity.ItemReportStatus;
import campus_lost_and_found_management_system.repository.ItemReportRepository;
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

    // D1: Safe saving
    public String save(ItemReport itemReport) {

        if (itemReport.getReportDate().isBefore(LocalDate.now())) {
            return "Item report date cannot be in the past";
        }

        if (itemReportRepository.existsByCustodian_IdAndReportDateAndStatusNot(
                itemReport.getCustodian().getId(),
                itemReport.getReportDate(),
                ItemReportStatus.WITHDRAWN)) {

            return "Custodian already handles an item report on that date";
        }

        itemReport.setStatus(ItemReportStatus.OPEN);
        itemReportRepository.save(itemReport);

        return "Item report saved successfully";
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

    // B2
    public List<ItemReport> findByStatus(ItemReportStatus status) {
        return itemReportRepository.findByStatusOrderByReportDateAsc(status);
    }

    // B3
    public List<ItemReport> findByDateBetween(
            LocalDate startDate, LocalDate endDate) {

        return itemReportRepository
                .findByReportDateBetweenOrderByReportDateAsc(startDate, endDate);
    }
    public ItemReport update(ItemReport itemReport) {
    return itemReportRepository.save(itemReport);
}
public String updateStatus(UUID id, ItemReportStatus newStatus) {

    Optional<ItemReport> optionalReport = itemReportRepository.findById(id);

    if (optionalReport.isEmpty()) {
        return "The item report with that id does not exist";
    }

    ItemReport report = optionalReport.get();
    ItemReportStatus currentStatus = report.getStatus();

    boolean allowed =
            (currentStatus == ItemReportStatus.OPEN &&
                    (newStatus == ItemReportStatus.MATCHED ||
                     newStatus == ItemReportStatus.WITHDRAWN))
            ||
            (currentStatus == ItemReportStatus.MATCHED &&
                    (newStatus == ItemReportStatus.RETURNED ||
                     newStatus == ItemReportStatus.WITHDRAWN));

    if (!allowed) {
        return "Cannot change status from " + currentStatus + " to " + newStatus;
    }

    report.setStatus(newStatus);
    itemReportRepository.save(report);

    return "Item report status updated successfully";
}
}