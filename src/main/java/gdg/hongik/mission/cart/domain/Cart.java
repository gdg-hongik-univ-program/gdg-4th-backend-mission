package gdg.hongik.mission.cart.domain;

import gdg.hongik.mission.ItemOrder.domain.ItemOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class Cart {
    Map<String, ItemOrder> itemOrders = new HashMap<>();

    public Cart(List<ItemOrder> itemOrders) {
        itemOrders.forEach(itemOrder -> this.itemOrders.put(itemOrder.getName(), itemOrder));
    }

    public ItemOrder getItemOrder(String name) {
        return itemOrders.get(name);
    }

    public List<String> getItemNames() {
        List<String> names = new java.util.ArrayList<>();
        this.getItemOrders().forEach((name, itemOrder) -> {names.add(name);});
        return names;
    }


    public void addItem(ItemOrder itemOrder) {
        itemOrders.put(itemOrder.getName(), itemOrder);
    }
    public void removeItem(String name) {
        itemOrders.remove(name);
    }
    public void decreaseQuantity(String name, int quantity) {
        if(quantity <= 0) { throw new IllegalArgumentException("quantity must be greater than 0");}

        itemOrders.get(name).decreaseQuantity(quantity);
    }
    public void increaseQuantity(String name, int quantity) {
        if(quantity <= 0) { throw new IllegalArgumentException("quantity must be greater than 0");}

        itemOrders.get(name).addQuantity(quantity);
    }
}
