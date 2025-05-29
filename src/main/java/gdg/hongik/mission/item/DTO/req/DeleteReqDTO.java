package gdg.hongik.mission.item.DTO.req;

import gdg.hongik.mission.enums.Auth;
import java.util.List;

public record DeleteReqDTO(String userName, Auth position, List<ItemReq> items) {
    public static record ItemReq(String itemName) {}
}
