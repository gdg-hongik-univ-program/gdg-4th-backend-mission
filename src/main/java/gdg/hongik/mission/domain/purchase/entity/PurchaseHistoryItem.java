package gdg.hongik.mission.domain.purchase.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 구매 기록 상품 정보를 나타내는 엔티티 클래스
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchaseHistoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_history_id")
    private PurchaseHistory purchaseHistory;

    /**
     * 구매 기록 상품 생성자
     * 
     * @param itemName 상품명
     * @param count 구매 수량
     * @param price 상품 가격
     */
    @Builder
    public PurchaseHistoryItem(String itemName, int count, int price) {
        this.itemName = itemName;
        this.count = count;
        this.price = price;
    }

    /**
     * 구매 기록을 설정합니다
     * 
     * @param purchaseHistory 구매 기록
     */
    public void setPurchaseHistory(PurchaseHistory purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }
} 