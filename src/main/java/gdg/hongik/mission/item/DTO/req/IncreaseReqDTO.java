package gdg.hongik.mission.item.DTO.req;

import gdg.hongik.mission.enums.Auth;

public record IncreaseReqDTO(String userName, Auth position, String itemName, int count) {
}
