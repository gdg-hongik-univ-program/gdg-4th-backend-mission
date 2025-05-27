package gdg.hongik.mission.domain.item.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 정보를 나타내는 엔티티 클래스
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) //이름당 하나의 물품만 존재해야함!
    private String name;

    @Column(nullable = false) //가격은 필수로 존재해야한다!
    private int price;

    @Column(nullable = false) //재고는 필수로 존재해야한다
    private int stock;

    /**
     * 상품 생성자
     * 
     * @param name 상품명
     * @param price 상품 가격
     * @param stock 상품 재고
     */
    @Builder
    public Item(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * 상품 재고를 추가합니다
     * 
     * @param count 추가할 재고 수량
     */
    public void addStock(int count) {
        this.stock += count;
    }

    /**
     * 상품 재고를 감소시킵니다
     * 
     * @param count 감소시킬 재고 수량
     * @throws IllegalArgumentException 재고가 부족한 경우
     */
    public void reduceStock(int count) {
        int restStock = this.stock - count;
        if (restStock < 0) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stock = restStock;
    }
} 