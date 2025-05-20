package com.example.shoppingmall.repository;

import com.example.shoppingmall.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    // 상품 이름으로 조회
    Optional<Item> findByName(String name);

    // 상품 이름으로 삭제
    void deleteByName(String name);

    // 필요한 경우 커스텀 쿼리도 정의 가능
}
