package gdg.hongik.mission.DTO;

import gdg.hongik.mission.entity.User.Role;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class RegisterItemRequest {
    private String name;
    private int price;
    private int stock;
    private Role position;
}