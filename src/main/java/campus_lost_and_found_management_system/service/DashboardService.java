
package campus_lost_and_found_management_system.service;

import campus_lost_and_found_management_system.entity.ItemReportStatus;
import campus_lost_and_found_management_system.repository.ReporterRepository;
import campus_lost_and_found_management_system.repository.CustodianRepository;
import campus_lost_and_found_management_system.repository.PostRepository;
import campus_lost_and_found_management_system.repository.ItemReportRepository;
import campus_lost_and_found_management_system.repository.ReturnReceiptRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final ReporterRepository reporterRepository;
    private final CustodianRepository custodianRepository;
    private final PostRepository postRepository;
    private final ItemReportRepository itemReportRepository;
    private final ReturnReceiptRepository returnReceiptRepository;

    public DashboardService(
            ReporterRepository reporterRepository,
            CustodianRepository custodianRepository,
            PostRepository postRepository,
            ItemReportRepository itemReportRepository,
            ReturnReceiptRepository returnReceiptRepository) {

        this.reporterRepository = reporterRepository;
        this.custodianRepository = custodianRepository;
        this.postRepository = postRepository;
        this.itemReportRepository = itemReportRepository;
        this.returnReceiptRepository = returnReceiptRepository;
    }

    public Map<String, Long> getSummary() {

        Map<String, Long> summary = new LinkedHashMap<>();

        summary.put("reporters", reporterRepository.countBy());
        summary.put("custodians", custodianRepository.countBy());
        summary.put("freePosts", (long) postRepository.findFreePosts().size());
        summary.put("reportsToday", itemReportRepository.countByReportDate(LocalDate.now()));
        summary.put("open", itemReportRepository.countByStatus(ItemReportStatus.OPEN));
        summary.put("returnReceipts", returnReceiptRepository.countBy());

        return summary;
    }
}
