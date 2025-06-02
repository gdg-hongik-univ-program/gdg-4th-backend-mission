package gdg.hongik.mission.domain.reservation.repository;

import gdg.hongik.mission.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 예약 정보에 대한 데이터 접근 인터페이스
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    /**
     * 사용자명으로 예약을 조회합니다
     * 
     * @param userName 사용자명
     * @return 예약 정보
     */
    Optional<Reservation> findByUserName(String userName);
    
    /**
     * 사용자명으로 예약이 존재하는지 확인합니다
     * 
     * @param userName 사용자명
     * @return 예약 존재 여부
     */
    boolean existsByUserName(String userName);
} 