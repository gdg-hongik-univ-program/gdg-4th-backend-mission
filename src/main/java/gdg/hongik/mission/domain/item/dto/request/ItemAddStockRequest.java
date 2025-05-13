package gdg.hongik.mission.domain.item.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemAddStockRequest {
    private String name;
    private String position;
    private int count;
} 