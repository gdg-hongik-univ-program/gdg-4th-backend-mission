package gdg.hongik.mission.domain.purchase.repository;

import gdg.hongik.mission.domain.purchase.entity.PurchaseHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 구매 기록에 대한 데이터 접근 인터페이스
 */
@Repository
public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
    
    /**
     * 사용자명으로 구매 기록을 조회합니다
     * 
     * @param userName 사용자명
     * @return 구매 기록 목록
     */
    List<PurchaseHistory> findByUserNameOrderByPurchaseTimeDesc(String userName);
} 