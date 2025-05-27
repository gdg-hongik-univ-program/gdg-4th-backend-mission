package gdg.hongik.mission.domain.item.repository;

import gdg.hongik.mission.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 상품 데이터 접근을 위한 리포지토리 인터페이스
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    
    /**
     * 상품명으로 상품을 조회합니다
     * 
     * @param name 조회할 상품명
     * @return 조회된 상품 (Optional)
     */
    Optional<Item> findByName(String name);
    
    /**
     * 상품명으로 상품 존재 여부를 확인합니다
     * 
     * @param name 확인할 상품명
     * @return 존재 여부 (true/false)
     */
    boolean existsByName(String name);
} 