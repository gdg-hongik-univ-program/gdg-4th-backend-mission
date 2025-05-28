package gdg.hongik.mission.purchase;

import jakarta.persistence.*;

public class PurchaseItem {
    @Entity
    @Table(name = "purchase_items")
    public class PurchaseItem {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private int count;

        @Column(nullable = false)
        private int price;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "record_id")
        private PurchaseRecord record;

        protected PurchaseItem() {}
        public PurchaseItem(String name, int count, int price) {
            this.name = name;
            this.count = count;
            this.price = price;
        }

        public Long getId() { return id; }
        public String getName() { return name; }
        public int getCount() { return count; }
        public int getPrice() { return price; }
        public PurchaseRecord getRecord() { return record; }
        public void setRecord(PurchaseRecord record) { this.record = record; }
    }
}
