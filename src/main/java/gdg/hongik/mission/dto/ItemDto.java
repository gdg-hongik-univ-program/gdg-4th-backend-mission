package gdg.hongik.mission.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 상품 정보를 담은 DTO
 * record 문법을 사용하여 setter 지양
 */
@Schema(description = "상품 정보 DTO")
public record ItemDto(

        @Schema(description = "상품 고유 ID", example = "1")
        Long id,

        @Schema(description = "상품 이름", example = "apple")
        String name,

        @Schema(description = "상품 가격", example = "1000")
        int price,

        @Schema(description = "상품 재고 수량", example = "100")
        int stock

) {}
