package com.example.shoppingmall.service;

import com.example.shoppingmall.model.Item;
import com.example.shoppingmall.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item findItemByName(String name) {
        return itemRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다: " + name));
    }

    public void registerItem(String name, int price, int stock, String position) {
        if (!"ADMIN".equalsIgnoreCase(position)) {
            throw new IllegalArgumentException("관리자만 등록할 수 있습니다.");
        }

        if (itemRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 상품입니다.");
        }

        itemRepository.save(new Item(name, price, stock));
    }

    public Item addStock(String name, int count, String position) {
        if (!"ADMIN".equalsIgnoreCase(position)) {
            throw new IllegalArgumentException("관리자만 재고를 추가할 수 있습니다.");
        }

        Item item = itemRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다: " + name));

        item.setStock(item.getStock() + count);
        return itemRepository.save(item);
    }

    public List<Item> deleteItems(List<String> names, String position) {
        if (!"ADMIN".equalsIgnoreCase(position)) {
            throw new IllegalArgumentException("관리자만 삭제할 수 있습니다.");
        }

        for (String name : names) {
            itemRepository.deleteByName(name);
        }

        return itemRepository.findAll();
    }
}
