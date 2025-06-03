package gdg.hongik.mission.item.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 상품 등록 요청 데이터를 전달하기 위한 DTO 클래스입니다.
 */
@Getter @Setter
public class ItemRequestDTO {

    /**
     * 상품 이름.
     */
    private String itemName;

    /**
     * 상품 가격.
     */
    private int price;

    /**
     * 상품 재고.
     */
    private int stock;
}
