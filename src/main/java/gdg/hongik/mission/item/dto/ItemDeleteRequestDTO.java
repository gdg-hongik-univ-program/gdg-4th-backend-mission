package gdg.hongik.mission.item.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ItemDeleteRequestDTO {
    private String name;          // 요청자 이름
    private String position;      // 요청자 역할
    private List<ItemNameDTO> items;

    @Getter @Setter
    public static class ItemNameDTO {
        private String name;      // 삭제할 아이템 이름
    }
}
