package gdg.hongik.mission.item.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 상품 구매 요청 데이터 전달을 위한 DTO 클래스입니다.
 * 구매자는 이름, 역할, 구매할 상품 목록을 포함하여 요청해야 합니다.
 */
@Getter @Setter
public class ItemBuyRequestDTO {
    private String name;         // 구매자 이름
    private String position;     // 구매자 역할 (ADMIN or CONSUMER)
    private List<ItemOrderDTO> items;

    /**
     * 구매 요청 내부의 아이템 처리 클래스입니다.
     */
    @Getter @Setter
    public static class ItemOrderDTO {
        private String name;     // 아이템 이름
        private int count;       // 구매 수량
    }
}
