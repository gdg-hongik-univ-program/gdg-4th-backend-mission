package gdg.hongik.mission.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 예약 상품 정보를 나타내는 엔티티 클래스
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    /**
     * 예약 상품 생성자
     * 
     * @param itemName 상품명
     * @param count 예약 수량
     */
    @Builder
    public ReservationItem(String itemName, int count) {
        this.itemName = itemName;
        this.count = count;
    }

    /**
     * 예약을 설정합니다
     * 
     * @param reservation 예약 정보
     */
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
} 