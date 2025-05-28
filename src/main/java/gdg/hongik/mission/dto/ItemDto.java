package gdg.hongik.mission.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    @Schema(description = "상품 고유 ID", example = "1")
    private Long id;
    @Schema(description = "상품 이름", example = "apple")
    private String name;
    @Schema(description = "상품 가격", example = "1000")
    private int price;
    @Schema(description = "상품 재고 수량", example = "100")
    private int stock;
}
