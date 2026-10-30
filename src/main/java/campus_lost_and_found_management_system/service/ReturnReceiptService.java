package campus_lost_and_found_management_system.service;

import campus_lost_and_found_management_system.entity.ItemReport;
import campus_lost_and_found_management_system.entity.ItemReportStatus;
import campus_lost_and_found_management_system.entity.ReturnReceipt;
import campus_lost_and_found_management_system.repository.ItemReportRepository;
import campus_lost_and_found_management_system.repository.ReturnReceiptRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReturnReceiptService {

    private final ReturnReceiptRepository returnReceiptRepository;
    private final ItemReportRepository itemReportRepository;

    public ReturnReceiptService(ReturnReceiptRepository returnReceiptRepository,
                                ItemReportRepository itemReportRepository) {
        this.returnReceiptRepository = returnReceiptRepository;
        this.itemReportRepository = itemReportRepository;
    }

    public String save(ReturnReceipt receipt, UUID itemReportId) {

        Optional<ItemReport> itemReport = itemReportRepository.findById(itemReportId);

        if (itemReport.isEmpty()) {
            return "The item report with that id does not exist";
        }

        if (itemReport.get().getStatus() != ItemReportStatus.RETURNED) {
            return "Return receipts are only for RETURNED item reports";
        }

        receipt.setIssuedDate(LocalDate.now());
        receipt.setItemReport(itemReport.get());

        returnReceiptRepository.save(receipt);

        return "Return receipt saved successfully";
    }

    public List<ReturnReceipt> findByReporterCampusId(String campusId) {
        return returnReceiptRepository.findByReporterCampusId(campusId);
    }

    public String delete(UUID id) {

        if (!returnReceiptRepository.existsById(id)) {
            return "The return receipt with that id does not exist";
        }

        returnReceiptRepository.deleteById(id);

        return "Return receipt deleted successfully";
    }
}