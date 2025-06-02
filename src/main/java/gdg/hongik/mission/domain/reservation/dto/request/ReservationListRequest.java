package gdg.hongik.mission.domain.reservation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 예약 목록 조회 요청 DTO
 */
@Schema(description = "예약 목록 조회 요청")
@Getter
@NoArgsConstructor
public class ReservationListRequest {

    @Schema(description = "관리자명", example = "지디지", required = true)
    private String name;

    @Schema(description = "사용자 권한", example = "ADMIN", required = true)
    private String position;
} 