package gdg.hongik.mission.ItemOrder.domain;

import gdg.hongik.mission.exception.CustomException;
import gdg.hongik.mission.exception.ErrorCode;
import gdg.hongik.mission.item.domain.Item;
import lombok.Getter;

@Getter
public class ItemOrder {
    String name;
    int quantity;

    public ItemOrder(String name, int quantity) {
        if (quantity <= 0) {
            throw new CustomException(ErrorCode.INPUT_MUST_BE_GREATER_THAN_ZERO);
        }
    }

    public void decreaseQuantity(int quantity) {
        if (quantity > this.quantity) { throw new CustomException(ErrorCode.INVALID_INPUT,
                "quantity must be less than or equal to the stock quantity");}
        this.quantity -= quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    static public ItemOrder from(Item item) {
        return new ItemOrder(item.getName(), item.getQuantity());
    }
}
