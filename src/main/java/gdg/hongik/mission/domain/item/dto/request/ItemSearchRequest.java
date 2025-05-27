package gdg.hongik.mission.domain.item.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 검색 요청 DTO 클래스
 */
@Getter
@NoArgsConstructor
public class ItemSearchRequest {
    
    /** 검색할 상품명 */
    private String name;
    
    /** 사용자 직책 (CONSUMER/ADMIN) */
    private String position;
} //모든 입력에는 이름과 직책이 적혀야한다!