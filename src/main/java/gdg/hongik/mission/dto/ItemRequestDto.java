package gdg.hongik.mission.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {

    @Schema(description = "상품 이름", example = "apple")
    private String name;

    @Schema(description = "요청자 직책(CONSUMER 또는 ADMIN)", example = "ADMIN")
    private String position; // CONSUMER or ADMIN

    @Schema(description = "상품 가격 (상품 등록에 사용)", example = "1000")
    private int price;

    @Schema(description = "초기 재고 수량 (상품 등록에 사용)", example = "100")
    private int stock;

    @Schema(description = "수량 정보 (재고 추가나 구매 요청에 사용)", example = "5")
    private int count; // 구매 or 추가시 사용
}
