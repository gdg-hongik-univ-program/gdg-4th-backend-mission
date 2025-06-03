package gdg.hongik.mission.DTO;

import gdg.hongik.mission.entity.User;

public record ReservationListRequest(
        String name,
        User.Role position
) {}
