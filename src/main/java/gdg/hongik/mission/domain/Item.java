package gdg.hongik.mission.domain;

import jakarta.persistence.*;


@Entity
@Table(
        name = "item", // DB 테이블명 지정
        uniqueConstraints = @UniqueConstraint(columnNames = "name") // name은 유니크 제약
)
public class Item {

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에서 자동 증가 (MySQL 등)
    private Long id; // 상품의 고유 ID

    @Column(nullable = false, unique = true) // null 불가, 유니크 제약
    private String name; // 상품 이름

    @Column(nullable = false)
    private int price;   // 상품 가격

    @Column(nullable = false)
    private int stock;   // 상품 재고(수량)

    public Item() {}

    public Item(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
}