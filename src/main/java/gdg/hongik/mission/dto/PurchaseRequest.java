package gdg.hongik.mission.dto;

import java.util.List;

public class PurchaseRequest {
    private List<PurchaseItem> items;

    public PurchaseRequest() {}

    public PurchaseRequest(List<PurchaseItem> items) {
        this.items = items;
    }

    public List<PurchaseItem> getItems() { return items; }
    public void setItems(List<PurchaseItem> items) { this.items = items; }

    public static class PurchaseItem {
        private String name;
        private int count;
        public PurchaseItem() {}
        public PurchaseItem(String name, int count) {
            this.name = name;
            this.count = count;
        }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getCount() { return count; }
        public void setCount(int count) { this.count = count; }
    }
}
