package gdg.hongik.mission.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {
    private String name;
    private String position; // CONSUMER or ADMIN
    private int price;
    private int stock;
    private int count; // 구매 or 추가시 사용
}
