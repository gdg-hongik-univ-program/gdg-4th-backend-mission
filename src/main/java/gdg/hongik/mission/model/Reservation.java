package gdg.hongik.mission.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * 소비자의 예약 정보
 * 한 명당 최대 1건 예약 가능
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 예약자 이름

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "reservation_items", joinColumns = @JoinColumn(name = "reservation_id"))
    private List<ReservedItem> items;

    public Reservation(String name, List<ReservedItem> items) {
        this.name = name;
        this.items = items;
    }
}
