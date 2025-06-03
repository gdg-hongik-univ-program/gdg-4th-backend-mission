package gdg.hongik.mission.item;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Spring Data JPA를 통해 CRUD 및 사용자 정의 쿼리 처리
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

    /**
     * 상품 이름으로 Item을 조회합니다.
     *
     * @param name 조회할 상품 이름
     * @return 이름에 해당하는 Item이 있으면 Optional로 반환, 없으면 빈 Optional 반환
     */
    Optional<Item> findByName(String name);

    /**
     * 상품 이름으로 존재 여부를 확인합니다.
     *
     * @param name 조회할 상품의 이름
     * @return 해당 이름의 Item이 존재하면 true, 아니면 false
     */
    boolean existsByName(String name);

    /**
     * 상품 이름으로 Item을 삭제합니다.
     *
     * @param name 삭제할 상품 이름
     */
    void deleteByName(String name);
}
