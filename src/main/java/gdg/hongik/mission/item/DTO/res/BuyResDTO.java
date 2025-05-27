package gdg.hongik.mission.item.DTO.res;

import gdg.hongik.mission.item.domain.Item;
import java.util.List;

public record BuyResDTO(int totalPrice, List<ItemDTO> items) {
    public record ItemDTO(String itemName, int price, int count) {}

    public static BuyResDTO from(int totalPrice, List<Item> Items) {
        List<ItemDTO> itemDTOs = Items.stream()
                .map(item -> new ItemDTO(item.getName(),
                        item.calculatePrice(item.getQuantity()),
                        item.getQuantity()))
                .toList();
        return new BuyResDTO(totalPrice, itemDTOs);
    }
}
