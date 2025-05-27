package com.example.shopbackend.repository;

import com.example.shopbackend.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Item 엔티티에 대한 JPA 리포지토리 인터페이스입니다.
 * 상품명을 기반으로 조회하는 기능을 제공합니다.
 */
@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    /**
     * 상품명을 기준으로 Item을 조회합니다.
     *
     * @param name 상품 이름
     * @return 해당 이름의 Item 객체 Optional
     */
    Optional<Item> findByName(String name);
}
