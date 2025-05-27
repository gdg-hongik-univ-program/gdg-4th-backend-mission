package gdg.hongik.mission.item.DTO.req;

import gdg.hongik.mission.enums.Auth;

public record RegisterReqDTO(String userName, Auth position, String itemName, int price, int stock) {
}
