package gdg.hongik.mission.item.DTO.res;

import java.util.List;

public record DeleteResDTO(List<ItemResDTO> items) {

    public static record ItemResDTO(String itemName, int stock) {}
}
