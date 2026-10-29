
package campus_lost_and_found_management_system.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "item_report")
public class ItemReport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate reportDate;

    @Enumerated(EnumType.STRING)
    private ItemReportStatus status;

    private String itemName;

    private String location;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    private Reporter reporter;

    @ManyToOne
    @JoinColumn(name = "custodian_id")
    private Custodian custodian;

    public ItemReport() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public ItemReportStatus getStatus() {
        return status;
    }

    public void setStatus(ItemReportStatus status) {
        this.status = status;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Reporter getReporter() {
        return reporter;
    }

    public void setReporter(Reporter reporter) {
        this.reporter = reporter;
    }

    public Custodian getCustodian() {
        return custodian;
    }

    public void setCustodian(Custodian custodian) {
        this.custodian = custodian;
    }
}
