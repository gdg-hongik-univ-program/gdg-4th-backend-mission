package gdg.hongik.mission.domain.item.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 재고 추가 요청 DTO 클래스
 */
@Getter
@NoArgsConstructor
public class ItemAddStockRequest {
    
    /** 상품명 */
    private String name;
    
    /** 사용자 직책 (CONSUMER/ADMIN) */
    private String position;
    
    /** 추가할 재고 수량 */
    private int count;
}//모든 입력에는 이름과 직책이 적혀야한다!