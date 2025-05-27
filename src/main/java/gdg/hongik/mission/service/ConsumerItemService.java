package gdg.hongik.mission.service;

import gdg.hongik.mission.DTO.PurchaseRequest;
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

    /**
     * 이름으로 상품을 검색합니다.
     * @param name 상품 이름
     * @return 검색된 상품
     */
    public Item findItem(String name) {
        return itemRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("상품 없음"));
    }

    /**
     * 소비자 또는 관리자가 상품을 구매합니다.
     * @param request 구매 요청 객체
     * @return 총 구매 금액 및 항목별 정보
     */
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

    /**
     * 모든 상품 목록과 재고를 조회합니다.
     * @return 상품 목록 리스트
     */
    public List<Map<String, Object>> getAll() {
        return itemRepository.findAll().stream().map(i -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", i.getName());
            map.put("stock", i.getStock());
            return map;
        }).toList();
    }
}
