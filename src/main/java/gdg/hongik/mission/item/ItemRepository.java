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

    /**
     * 아이템의 이름으로 아이템을 검색합니다.
     *
     * @param name 아이템 이름
     * @return 아이템이 존재하면 아이템 객체를, 그렇지 않으면 NULL을 반환합니다.
     */
    public Optional<Item> findByName(String name) {     // 아이템 이름으로 검색
        return Optional.ofNullable(items.get(name));
    }

    /**
     * 새로운 아이템을 등록합니다.
     *
     * @param name 등록할 아이템의 이름
     * @param price 등록할 아이템의 가격
     * @param stock 등록할 아이템의 개수
     * @return 등록한 아아템 객체를 반환합니다.
     */
    public Item save(String name,int price, int stock) {
        Item item = new Item(idCounter++, name, price, stock);
        items.put(item.getName(), item);
        return item;
    }

    /**
     * 이름을 통해 아이템을 삭제합니다.
     *
     * @param name 삭제할 아이템의 이름
     */
    public void deleteByName(String name) {
        items.remove(name);
    }

    /**
     * 등록되어있는 모든 아이템을 보여줍니다.
     *
     * @return 등록되어있는 모든 아이템 객체를 반환합니다.
     */
    public Collection<Item> findAll() {
        return items.values();
    }

    /**
     * 아이템이 존재하는지 여부를 알려줍니다.
     *
     * @param name 찾고자하는 아이템의 이름
     * @return 아이템이 존재하면 true, 그렇지 않으면 false를 반환합니다.
     */
    public boolean existsByName(String name) {
        return items.containsKey(name);
    }
}
