package gdg.hongik.mission.item.DTO;

import gdg.hongik.mission.enums.Auth;

public record RegisterReqDTO(String name, Auth position, String itemName, int price, int stock) {
}
