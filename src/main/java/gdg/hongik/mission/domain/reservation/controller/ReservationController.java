package gdg.hongik.mission.domain.reservation.controller;

import gdg.hongik.mission.domain.reservation.dto.request.ReservationCancelRequest;
import gdg.hongik.mission.domain.reservation.dto.request.ReservationCreateRequest;
import gdg.hongik.mission.domain.reservation.dto.request.ReservationListRequest;
import gdg.hongik.mission.domain.reservation.dto.response.ReservationListResponse;
import gdg.hongik.mission.domain.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 예약 관련 REST API를 제공하는 컨트롤러 클래스
 */
@Tag(name = "예약 관리", description = "예약 CRUD API")
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * 새로운 예약을 생성합니다
     * 
     * @param request 예약 생성 요청 정보
     * @return 204 상태 코드
     */
    @Operation(summary = "예약하기", description = "상품을 예약합니다. 소비자는 1개의 예약만 할 수 있습니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "예약 성공"),
        @ApiResponse(responseCode = "400", description = "이미 예약이 존재하거나 재고 부족")
    })
    @PostMapping
    public ResponseEntity<Void> createReservation(
            @Parameter(description = "예약 생성 요청 정보", required = true)
            @RequestBody ReservationCreateRequest request) {
        reservationService.createReservation(request);
        return ResponseEntity.noContent().build();
    }

    /**
     * 예약을 취소합니다
     * 
     * @param userName 예약자명
     * @param request 예약 취소 요청 정보
     * @return 204 상태 코드
     */
    @Operation(summary = "예약 취소하기", description = "본인 또는 관리자가 예약을 취소할 수 있습니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "예약 취소 성공"),
        @ApiResponse(responseCode = "400", description = "예약이 존재하지 않거나 권한 없음")
    })
    @DeleteMapping("/{userName}")
    public ResponseEntity<Void> cancelReservation(
            @Parameter(description = "예약자명", required = true)
            @PathVariable String userName,
            @Parameter(description = "예약 취소 요청 정보", required = true)
            @RequestBody ReservationCancelRequest request) {
        reservationService.cancelReservation(userName, request.getTarget());
        return ResponseEntity.noContent().build();
    }

    /**
     * 모든 예약 목록을 조회합니다 (관리자 전용)
     * 
     * @param request 예약 목록 조회 요청 정보
     * @return 예약 목록과 200 상태 코드
     */
    @Operation(summary = "예약 리스트 확인하기", description = "관리자가 모든 예약 목록을 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "예약 목록 조회 성공"),
        @ApiResponse(responseCode = "403", description = "관리자 권한 필요")
    })
    @PostMapping("/list")
    public ResponseEntity<ReservationListResponse> getReservationList(
            @Parameter(description = "예약 목록 조회 요청 정보", required = true)
            @RequestBody ReservationListRequest request) {
        // 관리자 권한 확인
        if (!"ADMIN".equals(request.getPosition())) {
            throw new IllegalArgumentException("관리자 권한이 필요합니다.");
        }
        
        ReservationListResponse response = reservationService.getAllReservations();
        return ResponseEntity.ok(response);
    }
} 