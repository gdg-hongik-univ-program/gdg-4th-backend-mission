package gdg.hongik.mission.domain.item.dto.response;

import gdg.hongik.mission.domain.item.entity.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * 상품 응답 DTO
 */
@Schema(description = "상품 응답")
@Getter
public class ItemResponse {
    
    @Schema(description = "상품 ID", example = "1")
    private final Long id;
    
    @Schema(description = "상품명", example = "노트북")
    private final String itemName;
    
    @Schema(description = "상품 가격", example = "1000000")
    private final int price;
    
    @Schema(description = "상품 재고", example = "10")
    private final int quantity;

    /**
     * ItemResponse 생성자
     * 
     * @param id 상품 ID
     * @param itemName 상품명
     * @param price 상품 가격
     * @param quantity 상품 재고
     */
    @Builder
    public ItemResponse(Long id, String itemName, int price, int quantity) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Item 엔티티를 ItemResponse로 변환합니다
     * 
     * @param item 변환할 Item 엔티티
     * @return 생성된 ItemResponse
     */
    public static ItemResponse of(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .itemName(item.getName())
                .price(item.getPrice())
                .quantity(item.getStock())
                .build();
    }
} 