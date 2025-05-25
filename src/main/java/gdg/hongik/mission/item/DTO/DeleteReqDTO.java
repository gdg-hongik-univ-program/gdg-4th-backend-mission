package gdg.hongik.mission.item.DTO;

import gdg.hongik.mission.enums.Auth;
import java.util.List;

public record DeleteReqDTO(String name, Auth position, List<ItemReq> items) {
    public static record ItemReq(String name) {}
}
