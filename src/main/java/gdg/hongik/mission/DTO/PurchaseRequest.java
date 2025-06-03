package gdg.hongik.mission.DTO;

import gdg.hongik.mission.entity.User.Role;
import lombok.*;
import java.util.List;

/**
 * 구매 요청 정보를 담는 DTO
 * 사용자는 구매자 또는 관리자일 수 있으며,
 * 구매할 상품 목록과 수량을 포함한다.
 */
public record PurchaseRequest(
        String name,
        Role position,
        List<ItemOrder> items
) {
    public record ItemOrder(String name, int count) { }
}
