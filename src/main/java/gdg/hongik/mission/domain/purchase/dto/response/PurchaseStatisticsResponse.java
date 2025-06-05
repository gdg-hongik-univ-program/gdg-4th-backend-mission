package gdg.hongik.mission.domain.purchase.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 구매 통계 응답 DTO
 */
@Schema(description = "구매 통계 응답")
@Getter
@AllArgsConstructor
public class PurchaseStatisticsResponse {

    @Schema(description = "상품별 구매 통계")
    private List<ItemStatistics> items;

    /**
     * 상품별 통계 정보
     */
    @Schema(description = "상품별 통계 정보")
    @Getter
    @AllArgsConstructor
    public static class ItemStatistics {
        
        @Schema(description = "상품명", example = "apple")
        private String name;
        
        @Schema(description = "총 구매 수량", example = "30")
        private int count;
        
        @Schema(description = "1번의 결제당 평균 구매 수", example = "3.4212")
        private double average;
    }
} 