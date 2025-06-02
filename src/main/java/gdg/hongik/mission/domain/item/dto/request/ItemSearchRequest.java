package gdg.hongik.mission.domain.item.dto.request;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 검색 요청 DTO
 */
@Schema(description = "상품 검색 요청")
@Getter
@NoArgsConstructor
public class ItemSearchRequest {

    @Schema(description = "사용자명", example = "홍길동", required = true)
    private String userName;

    @Schema(description = "사용자 권한", example = "USER", required = true)
    private String position;

    @Schema(description = "검색할 상품명", example = "노트북", required = true)
    private String itemName;
}