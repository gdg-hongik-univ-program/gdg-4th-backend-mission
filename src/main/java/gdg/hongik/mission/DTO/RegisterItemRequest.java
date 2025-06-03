package gdg.hongik.mission.DTO;

import gdg.hongik.mission.entity.User.Role;
import lombok.*;

/**
 * 상품 등록 요청 정보를 담는 DTO
 * 관리자만 사용할 수 있다.
 */
public record RegisterItemRequest(
        String name,
        int price,
        int stock,
        Role position
) { }
