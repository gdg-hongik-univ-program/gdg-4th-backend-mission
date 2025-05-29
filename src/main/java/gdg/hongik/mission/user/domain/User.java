package gdg.hongik.mission.user.domain;

import gdg.hongik.mission.enums.Auth;

public record User(String name, Auth auth) {
}
