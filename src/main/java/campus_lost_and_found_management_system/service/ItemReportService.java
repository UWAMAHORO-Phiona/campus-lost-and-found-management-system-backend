
package campus_lost_and_found_management_system.service;

import campus_lost_and_found_management_system.entity.ItemReport;
import campus_lost_and_found_management_system.entity.ItemReportStatus;
import campus_lost_and_found_management_system.repository.ItemReportRepository;
import campus_lost_and_found_management_system.repository.ReturnReceiptRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;

@Service
public class ItemReportService {

    private final ItemReportRepository itemReportRepository;
    private final ReturnReceiptRepository returnReceiptRepository;

    public ItemReportService(ItemReportRepository itemReportRepository,
                             ReturnReceiptRepository returnReceiptRepository) {
        this.itemReportRepository = itemReportRepository;
        this.returnReceiptRepository = returnReceiptRepository;
    }

    // D1: Save an item report safely
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

    // Get all item reports
    public List<ItemReport> findAll() {
        return itemReportRepository.findAll();
    }

    // Get an item report by ID
    public Optional<ItemReport> findById(UUID id) {
        return itemReportRepository.findById(id);
    }

    // A2: Update an item report without changing its status
    public String update(UUID id, ItemReport updatedReport) {

        Optional<ItemReport> optionalReport =
                itemReportRepository.findById(id);

        if (optionalReport.isEmpty()) {
            return "The item report with that id does not exist";
        }

        ItemReport existingReport = optionalReport.get();

        existingReport.setReportDate(updatedReport.getReportDate());
        existingReport.setItemName(updatedReport.getItemName());
        existingReport.setLocation(updatedReport.getLocation());
        existingReport.setReporter(updatedReport.getReporter());
        existingReport.setCustodian(updatedReport.getCustodian());

        // Keep the original status
        itemReportRepository.save(existingReport);

        return "Item report updated successfully";
    }

    // Delete an item report only when it has no return receipts
    public String delete(UUID id) {

        if (!itemReportRepository.existsById(id)) {
            return "The item report with that id does not exist";
        }

        if (returnReceiptRepository.existsByItemReport_Id(id)) {
            return "That item report has return receipts, delete them first";
        }

        itemReportRepository.deleteById(id);

        return "Item report deleted successfully";
    }

    // B2: Find item reports by status
    public List<ItemReport> findByStatus(ItemReportStatus status) {
        return itemReportRepository.findByStatusOrderByReportDateAsc(status);
    }

    // B3: Find item reports between two dates
    public List<ItemReport> findByDateBetween(
            LocalDate startDate, LocalDate endDate) {

        return itemReportRepository
                .findByReportDateBetweenOrderByReportDateAsc(startDate, endDate);
    }

    // D2: Update item report status
    public String updateStatus(UUID id, ItemReportStatus newStatus) {

        Optional<ItemReport> optionalReport =
                itemReportRepository.findById(id);

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

    // F1: Count item reports by status
    public List<Object[]> countItemReportsByStatus() {
        return itemReportRepository.countItemReportsByStatus();
    }

    // F2: Cancel reports for a custodian on a specific date
    @Transactional
    public String cancelReportsForCustodianOnDate(
            UUID custodianId, LocalDate date) {

        int count = itemReportRepository.cancelReportsForCustodianOnDate(
                custodianId,
                date,
                ItemReportStatus.WITHDRAWN,
                ItemReportStatus.RETURNED
        );

        return count + " item reports cancelled";
    }
    // Bonus: Get item reports page by page
public Page<ItemReport> findAll(Pageable pageable) {
    return itemReportRepository.findAll(pageable);
}
}
