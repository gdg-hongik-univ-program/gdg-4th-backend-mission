package gdg.hongik.mission.item;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item searchByName(String name) {
        return itemRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    public Map<String, Object> buy(String buyerName, String position, List<Map<String, Object>> items) {
        int totalPrice = 0;
        List<Map<String,Object>> resultItems = new ArrayList<>();

        for (Map<String, Object> entry : items) {
            String itemName = (String) entry.get("name");
            int count = (int) entry.get("count");
            Item item = itemRepository.findByName(itemName)
                    .orElseThrow(() -> new RuntimeException(itemName + " 상품이 존재하지 않습니다"));
            if (item.getStock() < count) {
                throw new RuntimeException(itemName + " 재고가 부족합니다");
            }
        }       // 상품에 대한 유효성 검사

        for (Map<String, Object> entry : items) {
            String itemName = (String) entry.get("name");
            int count = (int) entry.get("count");
            Item item = itemRepository.findByName(itemName)
                    .orElseThrow(() -> new RuntimeException("상품이 존재하지 않습니다"));
            if (item.getStock() < count) throw new RuntimeException(itemName + "재고가 부족합니다");
        }

        for(Map<String, Object> entry : items) {
            String itemName = (String) entry.get("name");
            int count = (int) entry.get("count");
            Item item = itemRepository.findByName(itemName).get();
            int price = count * item.getPrice();
            item.setStock(item.getStock() - count);

            Map<String, Object> itemResult = new HashMap<>();
            itemResult.put("name", item.getName());
            itemResult.put("count", count);
            itemResult.put("price", price);
            resultItems.add(itemResult);

            totalPrice += price;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("totalPrice", totalPrice);
        result.put("items", resultItems);
        return result;
    }

    public Item addStock(String requester, String position, String itemName, int count) {
        if(!"ADMIN".equalsIgnoreCase(position)) {
            throw new RuntimeException("관리자만 가능합니다");   // 소비자가 재고 추가 접근 시 예외처리
        }
        Item item = searchByName(itemName);
        item.setStock(item.getStock() + count);
        return item;
    }

    public List<Map<String, Object>> deleteItem(String requester, String position, List<Map<String,String>> toDelete) {
        if(!"ADMIN".equalsIgnoreCase(position)) {
            throw new RuntimeException("관리자만 가능합니다");   // 소비자가 재고 삭제 접근 시 예외처리
        }
        for(Map<String, String> entry : toDelete) {
            itemRepository.deleteByName(entry.get("name"));
        }
        List<Map<String,Object>> remaining = new ArrayList<>();
        for(Item item : itemRepository.findAll()) {
            Map<String, Object> obj = new HashMap<>();
            obj.put("name", item.getName());
            obj.put("stock", item.getStock());
            remaining.add(obj);
        }
        return remaining;
    }

    public void registerItem(String requester, String position, String itemName, int price, int stock) {
        if(!"ADMIN".equalsIgnoreCase(position)) {
            throw new RuntimeException("관리자만 가능합니다");
        }
        if(itemRepository.existsByName(itemName)) {
            throw new RuntimeException("이미 존재하는 상품입니다");
        }
        itemRepository.save(itemName, price, stock);
    }
}
