package gdg.hongik.mission.domain.item.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 상품 삭제 요청 DTO
 */
@Schema(description = "상품 삭제 요청")
@Getter
@NoArgsConstructor
public class ItemDeleteRequest {

    @Schema(description = "사용자명", example = "관리자", required = true)
    private String userName;

    @Schema(description = "사용자 권한", example = "ADMIN", required = true)
    private String position;

    @Schema(description = "삭제할 상품 목록", required = true)
    private List<DeleteItem> items;

    /**
     * 삭제할 상품 정보
     */
    @Schema(description = "삭제할 상품 정보")
    @Getter
    @NoArgsConstructor
    public static class DeleteItem {
        
        @Schema(description = "상품명", example = "노트북", required = true)
        private String itemName;
    }
} 