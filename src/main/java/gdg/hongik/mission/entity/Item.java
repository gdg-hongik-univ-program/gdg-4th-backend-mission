package gdg.hongik.mission.entity;

import jakarta.persistence.*;
        import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int price;
    private int stock;

    // 재고 감소 로직 캡슐화
    public void decreaseStock(int count) {
        if (stock < count) throw new IllegalArgumentException("재고 부족");
        this.stock -= count;
    }

    // 재고 증가 로직 캡슐화
    public void increaseStock(int count) {
        this.stock += count;
    }

    // Item.java
    public Item(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }


}
