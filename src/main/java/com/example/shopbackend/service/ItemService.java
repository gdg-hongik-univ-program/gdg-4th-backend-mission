package com.example.shopbackend.service;

import com.example.shopbackend.domain.Item;
import com.example.shopbackend.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item registerItem(Item item) {
        // 이미 존재하는 상품이면 예외 처리 (이름 중복 방지)
        Optional<Item> existing = itemRepository.findByName(item.getName());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이템입니다.");
        }
        return itemRepository.save(item);
    }

    public Item getItemByName(String name) {
        for (Item item : itemRepository.findAll()) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        throw new IllegalArgumentException("해당 이름의 아이템이 없습니다.");
    }

    public Item addStock(String name, int count) {
        Item item = getItemByName(name);
        item.setStock(item.getStock() + count);
        return itemRepository.save(item);
    }

    public void deleteItems(List<String> names) {
        List<Item> toDelete = new ArrayList<>();

        for (String name : names) {
            Item item = getItemByName(name);
            toDelete.add(item);
        }

        itemRepository.deleteAll(toDelete);
    }

    public Map<String, Object> purchaseItems(Map<String, Integer> items) {
        int totalPrice = 0;
        List<Map<String, Object>> itemList = new ArrayList<>();

        for (String name : items.keySet()) {
            int count = items.get(name);
            Item item = getItemByName(name);

            if (item.getStock() < count) {
                throw new IllegalArgumentException("재고 부족: " + name);
            }

            item.setStock(item.getStock() - count);
            itemRepository.save(item);

            Map<String, Object> result = new HashMap<>();
            result.put("name", name);
            result.put("count", count);
            result.put("price", count * item.getPrice());

            totalPrice += count * item.getPrice();
            itemList.add(result);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("totalPrice", totalPrice);
        response.put("items", itemList);

        return response;
    }
}
