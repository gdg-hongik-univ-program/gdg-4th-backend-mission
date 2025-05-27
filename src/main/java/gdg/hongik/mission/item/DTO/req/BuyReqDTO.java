package gdg.hongik.mission.item.DTO.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import gdg.hongik.mission.ItemOrder.domain.ItemOrder;
import java.util.List;

public record BuyReqDTO(String userName, String position, List<ItemOrderReq> items) {
    public record ItemOrderReq(
            String itemName,
            int count
    ) {}

    public List<ItemOrder> toDomainOrders() {
        return items.stream()
                .map(req -> new ItemOrder(req.itemName(), req.count()))
                .toList();
    }
}

