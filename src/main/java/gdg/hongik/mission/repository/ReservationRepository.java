package gdg.hongik.mission.repository;

import gdg.hongik.mission.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findByUsername(String username);
    void deleteByUsername(String username);
}