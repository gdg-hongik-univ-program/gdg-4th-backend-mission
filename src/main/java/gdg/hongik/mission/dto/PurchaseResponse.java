package gdg.hongik.mission.dto;

import java.util.List;

public class PurchaseResponse {
    private int totalPrice;
    private List<PurchasedItem> items;

    public PurchaseResponse() {}
    public PurchaseResponse(int totalPrice, List<PurchasedItem> items) {
        this.totalPrice = totalPrice;
        this.items = items;
    }
        public int getTotalPrice() { return totalPrice; }
    public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }
    public List<PurchasedItem> getItems() { return items; }
    public void setItems(List<PurchasedItem> items) { this.items = items; }

    public static class PurchasedItem {
        private String name;
        private int count;
        private int price;
        public PurchasedItem() {}
        public PurchasedItem(String name, int count, int price) {
            this.name = name;
            this.count = count;
            this.price = price;
        }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getCount() { return count; }
        public void setCount(int count) { this.count = count; }
        public int getPrice() { return price; }
        public void setPrice(int price) { this.price = price; }
    }
}