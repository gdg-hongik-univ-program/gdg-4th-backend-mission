package gdg.hongik.mission.dto;

import gdg.hongik.mission.purchase.PurchaseItem;

import java.util.List;

public class PurchaseResponse {
    private int totalPrice;
    private List<PurchaseItem> items;

    public PurchaseResponse(int totalPrice, List<PurchaseItem> items) {
        this.totalPrice = totalPrice;
        this.items = items;
    }
    public int getTotalPrice() { return totalPrice; }
    public List<PurchaseItem> getItems() { return items; }
}
