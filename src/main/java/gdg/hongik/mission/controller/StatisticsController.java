package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.PurchaseRecordDto;
import gdg.hongik.mission.dto.PurchaseStatisticsDto;
import gdg.hongik.mission.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Statistics API", description = "소비자 구매 기록 및 통계 조회 API")
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    /**
     * 특정 소비자의 구매 기록 조회
     */
    @Operation(summary = "구매 기록 조회", description = "소비자의 전체 구매 내역을 반환합니다.")
    @PostMapping("/records")
    public Map<String, List<PurchaseRecordDto>> getRecords(@RequestBody Map<String, String> request) {
        String target = request.get("target");
        List<PurchaseRecordDto> records = statisticsService.getPurchaseRecords(target);
        return Map.of("record", records);
    }

    /**
     * 특정 소비자의 구매 통계 조회
     */
    @Operation(summary = "구매 통계 조회", description = "소비자의 구매 수량 및 평균을 반환합니다.")
    @PostMapping("/summary")
    public Map<String, List<PurchaseStatisticsDto>> getStatistics(@RequestBody Map<String, String> request) {
        String target = request.get("target");
        List<PurchaseStatisticsDto> stats = statisticsService.getPurchaseStatistics(target);
        return Map.of("items", stats);
    }
}
