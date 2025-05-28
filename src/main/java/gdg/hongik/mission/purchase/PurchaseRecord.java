package gdg.hongik.mission.purchase;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "purchase_records")
public class PurchaseRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String consumer;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
    private List<PurchaseItem> items;

    protected PurchaseRecord() {}
    public PurchaseRecord(String consumer, List<PurchaseItem> items) {
        this.consumer = consumer;
        this.items = items;
        this.items.forEach(i -> i.setRecord(this));
    }

    public Long getId() { return id; }
    public String getConsumer() { return consumer; }
    public List<PurchaseItem> getItems() { return items; }
}
