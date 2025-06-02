package gdg.hongik.mission.domain.purchase.controller;

import gdg.hongik.mission.domain.purchase.dto.request.PurchaseHistoryRequest;
import gdg.hongik.mission.domain.purchase.dto.response.PurchaseHistoryResponse;
import gdg.hongik.mission.domain.purchase.dto.response.PurchaseStatisticsResponse;
import gdg.hongik.mission.domain.purchase.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 구매 기록 관련 REST API를 제공하는 컨트롤러 클래스
 */
@Tag(name = "구매 기록 관리", description = "구매 기록 및 통계 API")
@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PurchaseController {

    private final PurchaseService purchaseService;

    /**
     * 특정 소비자의 구매 기록을 조회합니다 (관리자 전용)
     * 
     * @param request 구매 기록 조회 요청 정보
     * @return 구매 기록과 200 상태 코드
     */
    @Operation(summary = "소비자 구매 기록", description = "특정 소비자의 구매 기록을 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "구매 기록 조회 성공"),
        @ApiResponse(responseCode = "403", description = "관리자 권한 필요")
    })
    @PostMapping("/history")
    public ResponseEntity<PurchaseHistoryResponse> getPurchaseHistory(
            @Parameter(description = "구매 기록 조회 요청 정보", required = true)
            @RequestBody PurchaseHistoryRequest request) {
        PurchaseHistoryResponse response = purchaseService.getPurchaseHistory(request.getTarget());
        return ResponseEntity.ok(response);
    }

    /**
     * 특정 소비자의 구매 통계를 조회합니다 (관리자 전용)
     * 
     * @param request 구매 통계 조회 요청 정보
     * @return 구매 통계와 200 상태 코드
     */
    @Operation(summary = "구매 기록 통계자료", description = "특정 소비자의 구매 통계를 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "구매 통계 조회 성공"),
        @ApiResponse(responseCode = "403", description = "관리자 권한 필요")
    })
    @PostMapping("/statistics")
    public ResponseEntity<PurchaseStatisticsResponse> getPurchaseStatistics(
            @Parameter(description = "구매 통계 조회 요청 정보", required = true)
            @RequestBody PurchaseHistoryRequest request) {
        PurchaseStatisticsResponse response = purchaseService.getPurchaseStatistics(request.getTarget());
        return ResponseEntity.ok(response);
    }
} 