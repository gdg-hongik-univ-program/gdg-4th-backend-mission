package gdg.hongik.mission.entity;

import gdg.hongik.mission.DTO.PurchaseRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "purchase_id")
    private List<PurchaseItem> items = new ArrayList<>();

    public PurchaseHistory(String username, List<PurchaseRequest.ItemOrder> orders) {
        this.username = username;
        for (PurchaseRequest.ItemOrder order : orders) {
            this.items.add(new PurchaseItem(order.name(), order.count()));
        }
    }
}