package gdg.hongik.mission.reservation;

import gdg.hongik.mission.item.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 예약 내역의 개별 상품 정보를 저장하는 엔티티 클래스입니다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private int count;

    /**
     * 예약 아이템 생성자.
     *
     * @param item 예약한 상품
     * @param count 예약 수량
     */
    public ReservationItem(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    /**
     * 예약 참조를 설정합니다.
     *
     * @param reservation 예약 객체
     */
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
