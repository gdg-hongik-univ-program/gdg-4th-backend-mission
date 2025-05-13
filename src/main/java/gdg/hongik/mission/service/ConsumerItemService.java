package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.PurchaseRequest;
import gdg.hongik.mission.entity.Item;
import gdg.hongik.mission.entity.User;
import gdg.hongik.mission.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConsumerItemService {
    private final ItemRepository itemRepository;

    public ConsumerItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item findItem(String name) {
        return itemRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("상품 없음"));
    }

    public Map<String, Object> purchase(PurchaseRequest request) {
        if (request.getPosition() != User.Role.CONSUMER && request.getPosition() != User.Role.ADMIN)
            throw new IllegalArgumentException("소비자 또는 관리자만 구매할 수 있습니다.");

        int total = 0;
        List<Map<String, Object>> resultItems = new ArrayList<>();

        for (PurchaseRequest.ItemOrder order : request.getItems()) {
            Item item = findItem(order.getName());
            if (item.getStock() < order.getCount())
                throw new IllegalArgumentException("재고 부족: " + item.getName());
            item.setStock(item.getStock() - order.getCount());
            itemRepository.save(item);

            int price = item.getPrice() * order.getCount();
            total += price;
            Map<String, Object> map = new HashMap<>();
            map.put("name", item.getName());
            map.put("count", order.getCount());
            map.put("price", price);
            resultItems.add(map);
        }

        return Map.of("totalPrice", total, "items", resultItems);
    }

    public List<Map<String, Object>> getAll() {
        return itemRepository.findAll().stream().map(i -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", i.getName());
            map.put("stock", i.getStock());
            return map;
        }).toList();
    }
}
