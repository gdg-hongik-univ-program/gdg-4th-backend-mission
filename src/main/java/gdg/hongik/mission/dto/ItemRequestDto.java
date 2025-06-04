package gdg.hongik.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 상품 등록, 재고 추가, 구매 시 사용하는 요청 DTO
 * record 문법으로 불변성과 간결성을 확보
 */
@Schema(description = "상품 요청 DTO")
public record ItemRequestDto(

        @Schema(description = "상품 이름", example = "apple")
        String name,

        @Schema(description = "요청자 직책(CONSUMER 또는 ADMIN)", example = "ADMIN")
        String position,

        @Schema(description = "상품 가격 (상품 등록에 사용)", example = "1000")
        int price,

        @Schema(description = "초기 재고 수량 (상품 등록에 사용)", example = "100")
        int stock,

        @Schema(description = "수량 정보 (재고 추가나 구매 요청에 사용)", example = "5")
        int count

) {}
