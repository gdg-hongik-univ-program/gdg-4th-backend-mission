package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.ItemDto;
import gdg.hongik.mission.model.Item;
import gdg.hongik.mission.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemDto findItem(String name) {
        Item item = itemRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        return new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStock());
    }

    public void registerItem(String name, int price, int stock, String position) {
        if (!"ADMIN".equalsIgnoreCase(position)) {
            throw new IllegalArgumentException("관리자만 등록할 수 있습니다.");
        }
        if (itemRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 상품입니다.");
        }
        itemRepository.save(new Item(null, name, price, stock));
    }

    public ItemDto addStock(String name, int count, String position) {
        if (!"ADMIN".equalsIgnoreCase(position)) {
            throw new IllegalArgumentException("관리자만 추가할 수 있습니다.");
        }
        Item item = itemRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
        item.setStock(item.getStock() + count);
        return new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStock());
    }

    public List<ItemDto> deleteItems(List<String> names, String position) {
        if (!"ADMIN".equalsIgnoreCase(position)) {
            throw new IllegalArgumentException("관리자만 삭제할 수 있습니다.");
        }

        names.forEach(name -> itemRepository.findByName(name).ifPresent(itemRepository::delete));
        return itemRepository.findAll().stream()
                .map(item -> new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStock()))
                .collect(Collectors.toList());
    }

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
