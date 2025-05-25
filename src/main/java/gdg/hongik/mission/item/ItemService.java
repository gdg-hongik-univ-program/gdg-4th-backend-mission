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

    /**
     * 이름을 통해 아이템을 검색합니다.
     *
     * @param name 찾고자 하는 아이템의 이름
     * @return 아이템이 존재한다면 그 아이템 객체를, 그렇지 않다면 런타임오류를 발생시킵니다.
     */
    public Item searchByName(String name) {
        return itemRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    /**
     * 아이템을 구매하는 로직을 수행합니다.
     *
     * @param buyerName 구매자의 이름
     * @param position 구매자의 포지션 (ADMIN or CONSUMER)
     * @param items 현재 아이템 재고 현황
     * @return 상품을 구입하는데 필요한 총 비용과 구매한 아이템의 이름, 가격, 남은 수량을 반환합니다.
     */
    public Map<String, Object> buy(String buyerName, String position, List<Map<String, Object>> items) {
        int totalPrice = 0;
        List<Map<String,Object>> resultItems = new ArrayList<>();

        // 상품에 대한 유효성 검사
        for (Map<String, Object> entry : items) {
            String itemName = (String) entry.get("name");
            int count = (int) entry.get("count");
            Item item = itemRepository.findByName(itemName)
                    .orElseThrow(() -> new RuntimeException(itemName + " 상품이 존재하지 않습니다"));
            if (item.getStock() < count) {
                throw new RuntimeException(itemName + " 재고가 부족합니다");
            }
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

    /**
     *  아이템의 재고를 추가합니다.
     *
     * @param requester 재고 추가 요청자 이름
     * @param position 재고 추가 요청자 역할 (ADMIN or CONSUMER)
     * @param itemName 추가 할 아이템 이름
     * @param count 추가 할 아이템 개수
     * @return 추가한 아이템 객체
     */
    public Item addStock(String requester, String position, String itemName, int count) {
        if(!"ADMIN".equalsIgnoreCase(position)) {
            throw new RuntimeException("관리자만 가능합니다");   // 소비자가 재고 추가 접근 시 예외처리
        }
        Item item = searchByName(itemName);
        item.setStock(item.getStock() + count);
        return item;
    }

    /**
     * 아이템 삭제
     *
     * @param requester 아이템 삭제 요청자의 이름
     * @param position 아이템 삭제 요청자의 역할 (ADMIN or CONSUMER)
     * @param toDelete 삭제할 아이템들의 이름을 담은 리스트
     * @return 남은 아이템들의 이름을 담은 리스트를 반환합니다.
     */
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

    /**
     * 아이템을 등록합니다.
     *
     * @param requester 요청자 이름
     * @param position 요청자 역할 (ADMIN or CONSUMER)
     * @param itemName 등록할 아이템 이름
     * @param price 등록할 아이템 가격
     * @param stock 등록할 아이템 개수
     */
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
