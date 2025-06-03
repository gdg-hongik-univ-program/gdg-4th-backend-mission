package gdg.hongik.mission.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * 예약 내역(Reservation)을 관리하는 JPA Repository 인터페이스입니다.
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * 특정 사용자의 예약 목록을 조회합니다.
     *
     * @param userId 사용자 ID
     * @return 예약 목록
     */
    List<Reservation> findByUserId(Long userId);
}
