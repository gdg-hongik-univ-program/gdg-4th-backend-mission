package gdg.hongik.mission.domain.item.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 상품 구매 요청 DTO
 */
@Schema(description = "상품 구매 요청")
@Getter
@NoArgsConstructor
public class ItemPurchaseRequest {

    @Schema(description = "사용자명", example = "홍길동", required = true)
    private String userName;

    @Schema(description = "사용자 권한", example = "USER", required = true)
    private String position;

    @Schema(description = "구매할 상품 목록", required = true)
    private List<PurchaseItem> items;

    /**
     * 구매할 상품 정보
     */
    @Schema(description = "구매할 상품 정보")
    @Getter
    @NoArgsConstructor
    public static class PurchaseItem {
        
        @Schema(description = "상품명", example = "노트북", required = true)
        private String itemName;
        
        @Schema(description = "구매 수량", example = "2", required = true)
        private int count;
    }
} 