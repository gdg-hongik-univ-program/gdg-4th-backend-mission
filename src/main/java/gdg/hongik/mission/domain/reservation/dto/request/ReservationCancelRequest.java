package gdg.hongik.mission.domain.reservation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 예약 취소 요청 DTO
 */
@Schema(description = "예약 취소 요청")
@Getter
@NoArgsConstructor
public class ReservationCancelRequest {

    @Schema(description = "취소 요청자 (예약자 본인 또는 관리자)", example = "지디지", required = true)
    private String target;
} 