package gdg.hongik.mission.item.DTO;

import java.util.List;

public record DeleteResDTO(List<ItemResDTO> items) {

    public static record ItemResDTO(String name, int stock) {}
}
