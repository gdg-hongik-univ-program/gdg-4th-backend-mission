package gdg.hongik.mission.model;

import jakarta.persistence.Embeddable;
import lombok.*;

/**
 * 예약한 개별 상품 항목
 */
@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservedItem {
    private String name;
    private int count;
}
