package gdg.hongik.mission.item;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ItemRepository {
    private final Map<String, Item> items = new HashMap<>();
    private int idCounter = 1;

    public Optional<Item> findByName(String name) {     // 아이템 이름으로 검색
        return Optional.ofNullable(items.get(name));
    }

    public Item save(String name,int price, int stock) {
        Item item = new Item(idCounter++, name, price, stock);
        items.put(item.getName(), item);
        return item;
    }

    public void deleteByName(String name) {
        items.remove(name);
    }

    public Collection<Item> findAll() {
        return items.values();
    }

    public boolean existsByName(String name) {
        return items.containsKey(name);
    }
}
