package gdg.hongik.mission.item.DTO;

import gdg.hongik.mission.enums.Auth;

public record IncreaseReqDTO(String name, Auth position, String itemName, int count) {
}
