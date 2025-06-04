package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.*;
import gdg.hongik.mission.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;

@Tag(name = "Reservation API", description = "상품 예약 관련 API")
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    /**
     * 예약하기
     */
    @Operation(summary = "상품 예약", description = "소비자가 상품을 예약합니다. 재고가 차감됩니다.")
    @PostMapping("/make")
    public void reserve(@RequestBody ReservationRequestDto request) {
        reservationService.makeReservation(request);
    }

    /**
     * 예약 취소
     */
    @Operation(summary = "예약 취소", description = "예약자는 본인 또는 관리자가 예약을 취소할 수 있습니다.")
    @PostMapping("/cancel")
    public void cancel(@RequestBody ReservationCancelDto request) {
        reservationService.cancelReservation(request.target());
    }

    /**
     * 전체 예약 목록 조회 (관리자 전용)
     */
    @Operation(summary = "예약 리스트 조회", description = "관리자만 모든 사용자의 예약 목록을 조회할 수 있습니다.")
    @PostMapping("/list")
    public Map<String, List<ReservationRequestDto>> getReservations(@RequestBody ReservationListRequestDto request) {
        return Map.of("data", reservationService.getAllReservations(request.name(), request.position()));
    }
}
