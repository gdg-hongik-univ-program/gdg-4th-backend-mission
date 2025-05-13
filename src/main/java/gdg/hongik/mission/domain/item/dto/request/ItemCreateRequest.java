package gdg.hongik.mission.domain.item.dto.request;

import gdg.hongik.mission.domain.item.entity.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemCreateRequest {
    private String name;
    private String position;
    private int price;
    private int stock;
    
    public Item toEntity() {
        return Item.builder()
                .name(name)
                .price(price)
                .stock(stock)
                .build();
    }
} 