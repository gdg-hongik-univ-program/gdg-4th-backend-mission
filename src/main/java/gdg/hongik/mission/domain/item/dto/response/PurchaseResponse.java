package gdg.hongik.mission.domain.item.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 상품 구매 응답 DTO
 */
@Schema(description = "상품 구매 응답")
@Getter
@AllArgsConstructor
public class PurchaseResponse {

    @Schema(description = "총 결제 금액", example = "2050000")
    private int totalPrice;

    @Schema(description = "구매한 상품 목록")
    private List<PurchasedItem> items;

    /**
     * 구매된 상품 정보
     */
    @Schema(description = "구매된 상품 정보")
    @Getter
    @AllArgsConstructor
    public static class PurchasedItem {
        
        @Schema(description = "상품명", example = "노트북")
        private String itemName;
        
        @Schema(description = "상품 가격", example = "1000000")
        private int price;
        
        @Schema(description = "구매 수량", example = "2")
        private int count;
    }
} 