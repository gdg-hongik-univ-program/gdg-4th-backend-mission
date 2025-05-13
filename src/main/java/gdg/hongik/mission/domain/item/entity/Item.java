package gdg.hongik.mission.domain.item.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    public Item(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public void addStock(int count) {
        this.stock += count;
    }

    public void reduceStock(int count) {
        int restStock = this.stock - count;
        if (restStock < 0) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stock = restStock;
    }
} 