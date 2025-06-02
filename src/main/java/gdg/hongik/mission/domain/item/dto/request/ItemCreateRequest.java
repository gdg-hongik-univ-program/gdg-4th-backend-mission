package gdg.hongik.mission.domain.item.dto.request;

import gdg.hongik.mission.domain.item.entity.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 생성 요청 DTO
 */
@Schema(description = "상품 등록 요청")
@Getter
@NoArgsConstructor
public class ItemCreateRequest {

    @Schema(description = "사용자명", example = "관리자", required = true)
    private String userName;

    @Schema(description = "사용자 권한", example = "ADMIN", required = true)
    private String position;

    @Schema(description = "상품명", example = "노트북", required = true)
    private String itemName;

    @Schema(description = "상품 가격", example = "1000000", required = true)
    private int price;

    @Schema(description = "상품 재고", example = "10", required = true)
    private int stock;

    /**
     * DTO를 Entity로 변환
     * 
     * @return Item 엔티티
     */
    public Item toEntity() {
        return Item.builder()
                .name(itemName)
                .price(price)
                .stock(stock)
                .build();
    }
}
