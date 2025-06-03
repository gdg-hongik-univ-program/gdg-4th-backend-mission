package gdg.hongik.mission.order;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * 구매 내역(Order)을 관리하는 JPA Repository 인터페이스입니다.
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * 특정 사용자의 주문 목록을 조회합니다.
     *
     * @param userId 사용자 ID
     * @return 주문 목록
     */
    List<Order> findByUserId(Long userId);
}
