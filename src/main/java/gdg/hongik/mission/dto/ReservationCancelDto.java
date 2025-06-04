package gdg.hongik.mission.dto;

/**
 * 예약 취소 요청 DTO
 * 본인 또는 관리자가 요청
 */
public record ReservationCancelDto(
        String target // 예약자 이름
) {}
