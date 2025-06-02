package gdg.hongik.mission.domain.purchase.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 구매 기록 응답 DTO
 */
@Schema(description = "구매 기록 응답")
@Getter
@AllArgsConstructor
public class PurchaseHistoryResponse {

    @Schema(description = "구매 기록 목록")
    private List<PurchaseRecord> record;

    /**
     * 구매 기록 정보
     */
    @Schema(description = "구매 기록 정보")
    @Getter
    @AllArgsConstructor
    public static class PurchaseRecord {
        
        @Schema(description = "구매한 상품 목록")
        private List<PurchaseItemData> items;
    }

    /**
     * 구매 상품 정보
     */
    @Schema(description = "구매 상품 정보")
    @Getter
    @AllArgsConstructor
    public static class PurchaseItemData {
        
        @Schema(description = "상품명", example = "apple")
        private String name;
        
        @Schema(description = "구매 수량", example = "5")
        private int count;
    }
} 