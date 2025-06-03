package gdg.hongik.mission.order;

import gdg.hongik.mission.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 구매 내역의 개별 상품 정보를 저장하는 엔티티 클래스입니다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private int count;

    /**
     * 주문 아이템 생성자.
     *
     * @param item 구매한 상품
     * @param count 구매 수량
     */
    public OrderItem(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    /**
     * 주문 참조를 설정합니다.
     *
     * @param order 주문 객체
     */
    public void setOrder(Order order) {
        this.order = order;
    }
}
