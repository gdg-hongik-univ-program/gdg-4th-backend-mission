package gdg.hongik.mission.item.repository;

import gdg.hongik.mission.item.domain.Item;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i "
            + "WHERE i.name IN (:names)")
    List<Item> findItemsByNames(@Param("names") List<String> names);
    Optional<Item> findItemByName(String name);
    void deleteByNameIn(List<String> name);

    List<Item> findAll();
}
