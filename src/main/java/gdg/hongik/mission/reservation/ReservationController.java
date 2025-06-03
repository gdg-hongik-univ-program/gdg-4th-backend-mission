package gdg.hongik.mission.reservation;

import gdg.hongik.mission.reservation.dto.ReservationRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 예약 요청을 처리하는 컨트롤러 클래스입니다.
 */
@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * 예약 API.
     *
     * @param request 예약 요청 DTO
     * @return 성공 메시지
     */
    @PostMapping("/make")
    public ResponseEntity<String> makeReservation(@RequestBody ReservationRequestDTO request) {
        reservationService.makeReservation(request);
        return ResponseEntity.ok("예약 완료");
    }
}
