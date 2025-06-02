package gdg.hongik.mission.domain.item.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 재고 추가 응답 DTO
 */
@Schema(description = "재고 추가 응답")
@Getter
@AllArgsConstructor
public class StockResponse {

    @Schema(description = "상품명", example = "노트북")
    private String itemName;

    @Schema(description = "현재 재고 수량", example = "15")
    private int count;
} 