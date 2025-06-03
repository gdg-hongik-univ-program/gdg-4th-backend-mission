package gdg.hongik.mission.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * 사용자 정보를 관리하는 JPA Repository 인터페이스입니다.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 사용자 이름으로 사용자 정보를 조회합니다.
     *
     * @param name 사용자 이름
     * @return 사용자 정보 (Optional)
     */
    Optional<User> findByName(String name);
}
