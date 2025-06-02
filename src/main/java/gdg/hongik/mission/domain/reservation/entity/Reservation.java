package gdg.hongik.mission.domain.reservation.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 예약 정보를 나타내는 엔티티 클래스
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private LocalDateTime reservationTime;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationItem> items = new ArrayList<>();

    /**
     * 예약 생성자
     * 
     * @param userName 예약자명
     */
    @Builder
    public Reservation(String userName) {
        this.userName = userName;
        this.reservationTime = LocalDateTime.now();
    }

    /**
     * 예약 상품을 추가합니다
     * 
     * @param item 예약 상품
     */
    public void addReservationItem(ReservationItem item) {
        this.items.add(item);
        item.setReservation(this);
    }
} 