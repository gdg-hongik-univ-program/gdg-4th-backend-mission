package gdg.hongik.mission.item.domain;

import gdg.hongik.mission.exception.CustomException;
import gdg.hongik.mission.exception.ErrorCode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Item(String name, int quantity, int price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    private String name;
    private int quantity;
    private int price;

    public void decreaseQuantity(int quantity) {
        if(quantity <= 0) { throw new CustomException(ErrorCode.INPUT_MUST_BE_GREATER_THAN_ZERO);}
        this.quantity -= quantity;
    }
    public void increaseQuantity(int quantity) {
        if(quantity <= 0) { throw new CustomException(ErrorCode.INPUT_MUST_BE_GREATER_THAN_ZERO); }
        this.quantity += quantity;
    }

    public int calculatePrice(int quantity) {
        return quantity * price;
    }
}
