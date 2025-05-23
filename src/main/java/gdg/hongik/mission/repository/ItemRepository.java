package gdg.hongik.mission.repository;

import gdg.hongik.mission.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByName(String name);

    boolean existsByName(String name);

    void deleteByName(String name);
}