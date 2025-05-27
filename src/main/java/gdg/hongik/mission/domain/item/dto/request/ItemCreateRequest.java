package gdg.hongik.mission.domain.item.dto.request;

import gdg.hongik.mission.domain.item.entity.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 생성 요청 DTO 클래스
 */
@Getter
@NoArgsConstructor
public class ItemCreateRequest {
    
    /** 상품명 */
    private String name;
    
    /** 사용자 직책 (CONSUMER/ADMIN) */
    private String position;
    
    /** 상품 가격 */
    private int price;
    
    /** 상품 재고 */
    private int stock;
    
    /**
     * DTO를 Item 엔티티로 변환합니다
     * 
     * @return 생성된 Item 엔티티
     */
    public Item toEntity() {
        return Item.builder()
                .name(name)
                .price(price)
                .stock(stock)
                .build();
    }
} //모든 입력에는 이름과 직책이 적혀야한다!
