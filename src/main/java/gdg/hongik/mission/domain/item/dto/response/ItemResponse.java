package gdg.hongik.mission.domain.item.dto.response;

import gdg.hongik.mission.domain.item.entity.Item;
import lombok.Builder;
import lombok.Getter;

/**
 * 상품 응답 DTO 클래스
 */
@Getter
public class ItemResponse {
    
    /** 상품 ID */
    private final Long id;
    
    /** 상품명 */
    private final String name;
    
    /** 상품 가격 */
    private final int price;
    
    /** 상품 재고 */
    private final int stock;

    /**
     * ItemResponse 생성자
     * 
     * @param id 상품 ID
     * @param name 상품명
     * @param price 상품 가격
     * @param stock 상품 재고
     */
    @Builder
    public ItemResponse(Long id, String name, int price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
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
                .name(item.getName())
                .price(item.getPrice())
                .stock(item.getStock())
                .build();
    }
} 