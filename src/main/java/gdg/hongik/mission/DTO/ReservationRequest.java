package gdg.hongik.mission.DTO;

import java.util.List;

public record ReservationRequest(
        String name, // 예약자 이름
        List<ItemOrder> items
) {
    public record ItemOrder(String name, int count) {}
}