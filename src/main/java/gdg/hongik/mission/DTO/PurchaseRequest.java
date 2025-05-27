package gdg.hongik.mission.DTO;

import gdg.hongik.mission.entity.User.Role;
import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PurchaseRequest {
    private String name;
    private Role position;
    private List<ItemOrder> items;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class ItemOrder {
        private String name;
        private int count;
    }
}