package gdg.hongik.mission.dto;

/**
 * 소비자의 누적 구매 통계 DTO
 */
public record PurchaseStatisticsDto(
        String name,
        int count,
        double average
) {}
