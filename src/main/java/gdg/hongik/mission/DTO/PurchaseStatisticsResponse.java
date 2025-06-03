package gdg.hongik.mission.DTO;

import java.util.List;

public record PurchaseStatisticsResponse(
        List<ItemStatistics> items
) {
    public record ItemStatistics(String name, int count, double average) {}
}