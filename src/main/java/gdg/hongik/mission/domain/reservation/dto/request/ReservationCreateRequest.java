package gdg.hongik.mission.domain.reservation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 예약 생성 요청 DTO
 */
@Schema(description = "예약 생성 요청")
@Getter
@NoArgsConstructor
public class ReservationCreateRequest {

    @Schema(description = "사용자명", example = "지디지", required = true)
    private String userName;

    @Schema(description = "예약할 상품 목록", required = true)
    private List<ReservationItem> items;

    /**
     * 예약할 상품 정보
     */
    @Schema(description = "예약할 상품 정보")
    @Getter
    @NoArgsConstructor
    public static class ReservationItem {
        
        @Schema(description = "상품명", example = "apple", required = true)
        private String name;
        
        @Schema(description = "예약 수량", example = "5", required = true)
        private int count;
    }
} 