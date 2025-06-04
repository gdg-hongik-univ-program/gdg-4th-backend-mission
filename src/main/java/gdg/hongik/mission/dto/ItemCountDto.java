package gdg.hongik.mission.dto;

/**
 * 상품 이름과 수량 정보를 담는 공통 DTO
 * 예약, 구매, 통계 등에서 재사용
 */
public record ItemCountDto(
        String name,
        int count
) {}
