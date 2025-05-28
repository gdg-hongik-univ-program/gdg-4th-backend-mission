package gdg.hongik.mission.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * 상품 정보를 저장하는 JAP 엔티티 클래스
 * 데이터베이스의 item 테이블과 매핑
 */
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private int price;

    private int stock;
}
