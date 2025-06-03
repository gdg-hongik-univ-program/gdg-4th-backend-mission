package gdg.hongik.mission.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 구매 요청 데이터를 전달하기 위한 DTO 클래스입니다.
 */
@Getter @Setter
public class BuyRequestDTO {

    /**
     * 구매자 이름.
     */
    private String name;

    /**
     * 구매자 역할.
     */
    private String position;

    /**
     * 구매할 상품 목록.
     */
    private List<ItemOrderDTO> items;

    /**
     * 개별 상품의 이름과 구매 수량 정보를 담은 내부 DTO 클래스입니다.
     */
    @Getter @Setter
    public static class ItemOrderDTO {
        private String name;
        private int count;
    }
}
