package gdg.hongik.mission.item.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import gdg.hongik.mission.ItemOrder.domain.ItemOrder;
import java.util.List;

public record BuyReqDTO(String name, String position, List<ItemOrderReq> items) {
    public static record ItemOrderReq(
            String name,
            @JsonProperty("count") int quantity
    ) {}

    public List<ItemOrder> toDomainOrders() {
        return items.stream()
                .map(req -> new ItemOrder(req.name(), req.quantity()))
                .toList();
    }
}

