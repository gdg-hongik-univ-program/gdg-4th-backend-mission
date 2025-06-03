package gdg.hongik.mission.item.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemChangePriceRequestDTO {
    private String name;         // 요청자 이름
    private String position;     // 요청자 역할
    private String itemName;     // 가격 변경할 아이템 이름
    private int newPrice;        // 새 가격
}
