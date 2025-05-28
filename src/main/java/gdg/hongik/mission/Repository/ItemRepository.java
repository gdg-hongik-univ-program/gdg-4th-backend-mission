package gdg.hongik.mission.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    boolean existsByName(String name);
    Optional<Item> findByName(String name);
}