package gdg.hongik.mission.dto;

import java.util.List;

public class DeleteResponse {
    private List<AddStockResponse> items;

    public DeleteResponse(List<AddStockResponse> items) {
        this.items = items;
    }
    public List<AddStockResponse> getItems() { return items; }
}
