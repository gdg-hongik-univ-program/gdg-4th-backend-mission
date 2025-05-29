package gdg.hongik.mission.item.DTO.res;

import gdg.hongik.mission.item.domain.Item;

public record SearchResDTO(Long id, String itemName, int quantity, int price) {
    public SearchResDTO(Item item) {
        this(item.getId(), item.getName(), item.getQuantity(), item.getPrice());
    }
}
