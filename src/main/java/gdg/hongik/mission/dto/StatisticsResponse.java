package gdg.hongik.mission.dto;

import java.util.List;

public class StatisticsResponse {
    private List<StatisticItem> items;

    public StatisticsResponse(List<StatisticItem> items) {
        this.items = items;
    }
    public List<StatisticItem> getItems() { return items; }
}
