package gdg.hongik.mission.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {
    @Id
    private String name;

    @Enumerated(EnumType.STRING)
    private Role position;

    public enum Role {
        CONSUMER, ADMIN
    }
}
