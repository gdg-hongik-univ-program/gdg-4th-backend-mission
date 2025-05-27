package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.ItemDto;
import gdg.hongik.mission.model.Item;
import gdg.hongik.mission.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 상품 관련 비즈니스 로직 처리 서비스 클래스
 * 상품 등록, 조회, 재고 추가, 삭제, 구매 기능을 포함함
 */

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * 이름으로 상품 조회
     * 
     * @param name 조회할 상품 이름
     * @return 상품 DTO
     */

    public ItemDto findItem(String name) {
        Item item = itemRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        return new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStock());
    }

    /**
     * 새 상품 등록
     * 
     * @param name 상품 이름
     * @param price 가격
     * @param stock 재고 수
     * @param position 요청자 직책책
     */

    public void registerItem(String name, int price, int stock, String position) {
        if (!"ADMIN".equalsIgnoreCase(position)) {
            throw new IllegalArgumentException("관리자만 등록할 수 있습니다.");
        }
        if (itemRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 상품입니다.");
        }
        itemRepository.save(new Item(null, name, price, stock));
    }
    /**
     * 재고 추가
     * @param name 상품 이름
     * @param count 추가 수량
     * @param position 요청자 직책
     * @return 업데이트된 상품품
     */

    public ItemDto addStock(String name, int count, String position) {
        if (!"ADMIN".equalsIgnoreCase(position)) {
            throw new IllegalArgumentException("관리자만 추가할 수 있습니다.");
        }
        Item item = itemRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
        item.setStock(item.getStock() + count);
        return new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStock());
    }

    /**
     * 여러 상품 삭제
     * @param names 삭제할 상품 이름 리스트
     * @param position 요청자 직책
     * @return 삭제 후 남은 상품품
     */

    public List<ItemDto> deleteItems(List<String> names, String position) {
        if (!"ADMIN".equalsIgnoreCase(position)) {
            throw new IllegalArgumentException("관리자만 삭제할 수 있습니다.");
        }

        names.forEach(name -> itemRepository.findByName(name).ifPresent(itemRepository::delete));
        return itemRepository.findAll().stream()
                .map(item -> new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStock()))
                .collect(Collectors.toList());
    }

    /**
     * 상품 구매를 처리합니다.
     * 
     * @param items 구매 요청 목록(상품 이름, 수량 포함)
     * @return 총 가격 및 구매 상세 목록
     */

    public Map<String, Object> purchaseItems(List<Map<String, Object>> items) {
        int totalPrice = 0;
        List<Map<String, Object>> resultItems = new ArrayList<>();

        for (Map<String, Object> i : items) {
            String name = (String) i.get("name");
            int count = (Integer) i.get("count");
            Item item = itemRepository.findByName(name)
                    .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
            if (item.getStock() < count) {
                throw new IllegalArgumentException("재고가 부족합니다: " + name);
            }
            item.setStock(item.getStock() - count);
            int price = count * item.getPrice();
            totalPrice += price;
            Map<String, Object> itemResult = new HashMap<>();
            itemResult.put("name", name);
            itemResult.put("count", count);
            itemResult.put("price", price);
            resultItems.add(itemResult);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("totalPrice", totalPrice);
        response.put("items", resultItems);
        return response;
    }
}
