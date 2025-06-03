package gdg.hongik.mission.controller;

import gdg.hongik.mission.DTO.PurchaseRequest;
import gdg.hongik.mission.DTO.UserRequest;
import gdg.hongik.mission.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "예약하기", description = "소비자가 재고를 미리 예약합니다.")
    @PostMapping("/reserve")
    public void reserve(@RequestBody PurchaseRequest request) {
        reservationService.reserve(request.name(), request.items());
    }

    @Operation(summary = "예약 취소", description = "소비자 또는 관리자가 예약을 취소합니다.")
    @PostMapping("/cancel")
    public void cancelReservation(@RequestBody UserRequest request) {
        reservationService.cancelReservation(request);
    }

    @Operation(summary = "예약 목록 확인", description = "관리자가 전체 예약 내역을 확인합니다.")
    @PostMapping("/list")
    public Map<String, Object> findAllReservations(@RequestBody UserRequest request) {
        return Map.of("data", result);  //

    }

    @Operation(summary = "구매 통계 조회", description = "사용자의 구매 통계를 확인합니다.")
    @PostMapping("/statistics")
    public Map<String, Object> getPurchaseStatistics(@RequestBody UserRequest request) {
        return reservationService.getPurchaseStatistics(request);
    }
}
