package gdg.hongik.mission.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 정보를 저장하는 엔티티 클래스입니다.
 * 이름과 역할(ADMIN/CONSUMER)을 저장합니다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String position; // ADMIN or CONSUMER

    /**
     * User 객체 생성자.
     *
     * @param name 사용자 이름
     * @param position 사용자 역할 (ADMIN or CONSUMER)
     */
    public User(String name, String position) {
        this.name = name;
        this.position = position;
    }
}
