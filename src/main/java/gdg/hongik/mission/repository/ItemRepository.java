package gdg.hongik.mission.repository;

import gdg.hongik.mission.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * ItemRepository
 * Item 엔티티에 대한 DB 접근(JPA 지원) 레이어
 */
public interface ItemRepository extends JpaRepository<Item, Long> {
    // 상품 이름으로 Item 조회 (Optional: 없을 경우 empty 반환)
    Optional<Item> findByName(String name);

    // 상품 이름으로 존재 여부 빠르게 확인 (중복 체크 등에 사용)
    boolean existsByName(String name);

    // 상품 이름으로 삭제
    void deleteByName(String name);
}