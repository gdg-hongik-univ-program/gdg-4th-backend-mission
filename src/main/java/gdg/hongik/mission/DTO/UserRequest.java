package gdg.hongik.mission.DTO;

import gdg.hongik.mission.entity.User;

public record UserRequest(
        String name,
        User.Role position
) {}
