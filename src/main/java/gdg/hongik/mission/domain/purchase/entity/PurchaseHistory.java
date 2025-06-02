package gdg.hongik.mission.domain.purchase.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 구매 기록을 나타내는 엔티티 클래스
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private LocalDateTime purchaseTime;

    @OneToMany(mappedBy = "purchaseHistory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseHistoryItem> items = new ArrayList<>();

    /**
     * 구매 기록 생성자
     * 
     * @param userName 구매자명
     * @param totalPrice 총 결제 금액
     */
    @Builder
    public PurchaseHistory(String userName, int totalPrice) {
        this.userName = userName;
        this.totalPrice = totalPrice;
        this.purchaseTime = LocalDateTime.now();
    }

    /**
     * 구매 상품을 추가합니다
     * 
     * @param item 구매 상품
     */
    public void addPurchaseItem(PurchaseHistoryItem item) {
        this.items.add(item);
        item.setPurchaseHistory(this);
    }
} 