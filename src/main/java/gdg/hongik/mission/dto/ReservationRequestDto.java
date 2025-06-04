package gdg.hongik.mission.dto;

import java.util.List;

/**
 * 예약 요청 DTO
 * 소비자가 예약할 때 사용
 */
public record ReservationRequestDto(
        String name,
        List<ItemCountDto> items
) {}
