package gdg.hongik.mission.cart.domain;

import gdg.hongik.mission.ItemOrder.domain.ItemOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        return new ArrayList<>(itemOrders.keySet());
    }


   public Map<String, Integer> getSortOrders() {
       List<String> names = this.getItemNames();
       return IntStream.range(0, names.size())
               .boxed()
               .collect(Collectors.toMap(names::get, Function.identity()));
   }
   public int getQuantity(String itemName) {
       return itemOrders.get(itemName).getQuantity();
   }
}
