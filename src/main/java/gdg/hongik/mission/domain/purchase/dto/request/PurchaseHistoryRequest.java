package gdg.hongik.mission.domain.purchase.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 구매 기록 조회 요청 DTO
 */
@Schema(description = "구매 기록 조회 요청")
@Getter
@NoArgsConstructor
public class PurchaseHistoryRequest {

    @Schema(description = "검색할 소비자명", example = "김와우", required = true)
    private String target;
} 