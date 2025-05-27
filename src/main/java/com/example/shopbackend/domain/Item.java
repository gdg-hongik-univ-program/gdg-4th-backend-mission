package com.example.shopbackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

/**
 * 쇼핑몰에서 사용하는 상품(Item)의 엔티티 클래스입니다.
 */
@Getter
@Setter
@Entity
public class Item {

    /**
     * 상품 고유 ID (자동 생성)
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 상품 이름
     */
    private String name;

    /**
     * 상품 가격
     */
    private int price;

    /**
     * 상품 재고 수량
     */
    private int stock;

}
