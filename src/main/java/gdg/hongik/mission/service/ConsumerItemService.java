package gdg.hongik.mission.service;

import gdg.hongik.mission.DTO.PurchaseRequest;
import gdg.hongik.mission.entity.Item;
import gdg.hongik.mission.entity.User;
import gdg.hongik.mission.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Map<String, Object> purchase(PurchaseRequest request) {
        if (request.position() != User.Role.CONSUMER && request.position() != User.Role.ADMIN) {
            throw new IllegalArgumentException("소비자 또는 관리자만 구매할 수 있습니다.");
        }

        int total = 0;
        List<Map<String, Object>> resultItems = new ArrayList<>();

        for (PurchaseRequest.ItemOrder order : request.items()) {
            Item item = findItem(order.name());
            if (item.getStock() < order.count()) {
                throw new IllegalArgumentException("재고 부족: " + item.getName());
            }

            item.decreaseStock(order.count());
            itemRepository.save(item);

            int price = item.getPrice() * order.count();
            total += price;

            Map<String, Object> map = new HashMap<>();
            map.put("name", item.getName());
            map.put("count", order.count());
            map.put("price", price);
            resultItems.add(map);
        }

        return Map.of("totalPrice", total, "items", resultItems);
    }
}
