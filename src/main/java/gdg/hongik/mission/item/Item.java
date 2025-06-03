package gdg.hongik.mission.item;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    /**
     * 상품 고유 식별자 (Primary Key)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 상품 이름 (중복 불가)
     */
    @Column(nullable = false, unique = true)
    private String name;

    /**
     * 상품 가격
     */
    private int price;

    /**
     * 상품 재고
     */
    private int stock;

    /**
     * Item 객체 생성자
     *
     * @param name 상품 이름
     * @param price 상품 가격
     * @param stock 상품 재고
     * @throws IllegalArgumentException 가격이 음수인경우
     * @throws IllegalArgumentException 재고가 음수인 경우
     */
    public Item(String name, int price, int stock) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
        }
        if (stock <= 0) {
            throw new IllegalArgumentException("재고는 0보다 커야 합니다.");
        }
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * 상품의 재고를 감소시킵니다.
     *
     * @param count 감소할 수량
     * @throws IllegalArgumentException 재고가 부족한 경우
     */
    public void decreaseStock(int count) {
        if (count > this.stock) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.stock -= count;
    }

    /**
     * 상품의 재고를 증가시킵니다.
     *
     * @param count 감소할 수량
     * @throws IllegalArgumentException 증가시킬 재고가 0개 이하인 경우
     */
    public void increaseStock(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("추가할 재고는 1개 이상이어야 합니다.");
        }
        this.stock += count;
    }

    /**
     * 상품의 가격을 변경합니다.
     *
     * @param newPrice 새로 설정할 가격 (0이상이어야 함)
     * @throws IllegalArgumentException 가격이 0 미만인 경우
     */
    public void changePrice(int newPrice) {
        if (newPrice < 0) {
            throw new IllegalArgumentException("가격은 0보다 크거나 같아야 합니다.");
        }
        this.price = newPrice;
    }
}
