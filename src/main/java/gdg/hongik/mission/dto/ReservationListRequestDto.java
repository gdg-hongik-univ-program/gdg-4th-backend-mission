package gdg.hongik.mission.dto;

/**
 * 관리자 예약 목록 조회 요청 DTO
 */
public record ReservationListRequestDto(
        String name,     // 관리자 이름
        String position  // "ADMIN"만 허용
) {}
