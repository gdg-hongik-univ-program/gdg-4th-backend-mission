package gdg.hongik.mission.dto;

import java.util.List;

public class RemainingItemsResponse {
    private List<ItemResponse> items;
    public RemainingItemsResponse() {}
    public RemainingItemsResponse(List<ItemResponse> items) { this.items = items; }
    public List<ItemResponse> getItems() { return items; }
    public void setItems(List<ItemResponse> items) { this.items = items; }
}