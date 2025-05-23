package com.example.shopbackend.controller;

import com.example.shopbackend.domain.Item;
import com.example.shopbackend.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    private void assertAdmin(String position) {
        if (!"ADMIN".equals(position)) {
            throw new IllegalArgumentException("관리자만 사용할 수 있는 기능입니다.");
        }
    }

    @PostMapping
    public Item registerItem(@RequestBody Map<String, Object> req) {
        String position = (String) req.get("position");
        assertAdmin(position);

        Map<String, Object> itemData = (Map<String, Object>) req.get("item");
        Item item = new Item();
        item.setName((String) itemData.get("name"));
        item.setPrice((int) itemData.get("price"));
        item.setStock((int) itemData.get("stock"));

        return itemService.registerItem(item);
    }

    @GetMapping("/{name}")
    public Item getItem(@PathVariable String name) {
        return itemService.getItemByName(name);
    }

    @PostMapping("/add-stock")
    public Item addStock(@RequestBody Map<String, Object> req) {
        String position = (String) req.get("position");
        assertAdmin(position);

        String name = (String) req.get("name");
        int count = (int) req.get("count");

        return itemService.addStock(name, count);
    }

    @DeleteMapping
    public void deleteItems(@RequestBody Map<String, Object> req) {
        String position = (String) req.get("position");
        assertAdmin(position);

        List<Map<String, String>> items = (List<Map<String, String>>) req.get("items");
        List<String> names = new ArrayList<>();

        for (Map<String, String> item : items) {
            names.add(item.get("name"));
        }

        itemService.deleteItems(names);
    }

    @PostMapping("/purchase")
    public Map<String, Object> purchaseItems(@RequestBody Map<String, Integer> items) {
        return itemService.purchaseItems(items);
    }
}
