package gdg.hongik.mission.item.DTO;

import gdg.hongik.mission.item.domain.Item;
import java.util.List;

public record BuyResDTO(int totalPrice, List<Item> Items) {
}
