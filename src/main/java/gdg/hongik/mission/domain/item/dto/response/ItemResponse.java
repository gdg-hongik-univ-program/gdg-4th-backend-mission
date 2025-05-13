package gdg.hongik.mission.domain.item.dto.response;
import gdg.hongik.mission.domain.item.entity.Item;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemResponse {
    private final Long id;
    private final String name;
    private final int price;
    private final int stock;

    @Builder
    public ItemResponse(Long id, String name, int price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public static ItemResponse of(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .stock(item.getStock())
                .build();
    }
} 