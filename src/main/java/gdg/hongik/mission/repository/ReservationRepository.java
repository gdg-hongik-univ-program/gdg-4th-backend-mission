package gdg.hongik.mission.repository;

import gdg.hongik.mission.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * 예약자 이름으로 예약을 조회합니다.
     * (한 명당 1건만 가능하므로 Optional)
     */
    Optional<Reservation> findByName(String name);

    /**
     * 예약자 이름으로 예약을 삭제합니다.
     */
    void deleteByName(String name);

    /**
     * 예약 존재 여부 확인
     */
    boolean existsByName(String name);
}
