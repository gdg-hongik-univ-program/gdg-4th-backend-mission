package gdg.hongik.mission.repository;

import gdg.hongik.mission.entity.item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    boolean existsByName(String name);
    void deleteByName(String name);
}