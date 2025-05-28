package gdg.hongik.mission.repository;

import gdg.hongik.mission.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByConsumer(String consumer);
}
