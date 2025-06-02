package gdg.hongik.mission.domain.item.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 재고 추가 요청 DTO
 */
@Schema(description = "상품 재고 추가 요청")
@Getter
@NoArgsConstructor
public class ItemAddStockRequest {

    @Schema(description = "사용자명", example = "관리자", required = true)
    private String userName;

    @Schema(description = "사용자 권한", example = "ADMIN", required = true)
    private String position;

    @Schema(description = "상품명", example = "노트북", required = true)
    private String itemName;

    @Schema(description = "추가할 재고 수량", example = "5", required = true)
    private int count;
}