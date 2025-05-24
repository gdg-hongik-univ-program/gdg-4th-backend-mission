package gdg.hongik.mission.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private int id;

    @Column(unique = true, nullable = false)
    private String name;  // 상품 이름

    @Column(nullable = false)
    private int price;    // 가격

    @Column(nullable = false)
    private int stock;    // 재고 수량
}
