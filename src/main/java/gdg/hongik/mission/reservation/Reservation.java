package gdg.hongik.mission.reservation;

import gdg.hongik.mission.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 예약 정보를 저장하는 엔티티 클래스입니다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationItem> reservationItems = new ArrayList<>();

    /**
     * Reservation 객체 생성자.
     *
     * @param user 예약자
     */
    public Reservation(User user) {
        this.user = user;
    }

    /**
     * 예약 아이템을 예약에 추가합니다.
     *
     * @param reservationItem 예약 아이템
     */
    public void addReservationItem(ReservationItem reservationItem) {
        reservationItems.add(reservationItem);
        reservationItem.setReservation(this);
    }
}
