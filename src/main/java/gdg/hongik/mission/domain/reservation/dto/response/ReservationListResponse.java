package gdg.hongik.mission.domain.reservation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 예약 목록 응답 DTO
 */
@Schema(description = "예약 목록 응답")
@Getter
@AllArgsConstructor
public class ReservationListResponse {

    @Schema(description = "예약 목록")
    private List<ReservationData> data;

    /**
     * 예약 정보
     */
    @Schema(description = "예약 정보")
    @Getter
    @AllArgsConstructor
    public static class ReservationData {
        
        @Schema(description = "예약자명", example = "지디지")
        private String name;
        
        @Schema(description = "예약 상품 목록")
        private List<ReservationItemData> items;
    }

    /**
     * 예약 상품 정보
     */
    @Schema(description = "예약 상품 정보")
    @Getter
    @AllArgsConstructor
    public static class ReservationItemData {
        
        @Schema(description = "상품명", example = "apple")
        private String name;
        
        @Schema(description = "예약 수량", example = "5")
        private int count;
    }
} 