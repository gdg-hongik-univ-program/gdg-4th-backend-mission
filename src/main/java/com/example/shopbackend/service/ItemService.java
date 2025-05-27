package com.example.shopbackend.service;

import com.example.shopbackend.domain.Item;
import com.example.shopbackend.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * 상품 등록, 조회, 재고 추가, 삭제, 구매에 대한 비즈니스 로직을 담당하는 서비스 클래스입니다.
 */
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * 새로운 상품을 등록합니다.
     * 이미 존재하는 상품명이 있을 경우 예외를 발생시킵니다.
     *
     * @param item 등록할 상품 객체
     * @return 저장된 상품 객체
     */
    public Item registerItem(Item item) {
        // 이미 존재하는 상품이면 예외 처리 (이름 중복 방지)
        Optional<Item> existing = itemRepository.findByName(item.getName());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이템입니다.");
        }
        return itemRepository.save(item);
    }

    /**
     * 상품명을 기준으로 상품을 조회합니다.
     *
     * @param name 조회할 상품의 이름
     * @return 해당 이름의 상품 객체
     */
    public Item getItemByName(String name) {
        for (Item item : itemRepository.findAll()) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        throw new IllegalArgumentException("해당 이름의 아이템이 없습니다.");
    }

    /**
     * 기존 상품의 재고를 추가합니다.
     *
     * @param name 상품 이름
     * @param count 추가할 재고 수량
     * @return 재고가 추가된 상품 객체
     */
    public Item addStock(String name, int count) {
        Item item = getItemByName(name);
        item.setStock(item.getStock() + count);
        return itemRepository.save(item);
    }

    /**
     * 이름 목록에 해당하는 상품들을 삭제합니다.
     *
     * @param names 삭제할 상품 이름 목록
     */
    public void deleteItems(List<String> names) {
        List<Item> toDelete = new ArrayList<>();

        for (String name : names) {
            Item item = getItemByName(name);
            toDelete.add(item);
        }

        itemRepository.deleteAll(toDelete);
    }

    /**
     * 상품들을 구매 처리하고 총액과 상세 항목을 반환합니다.
     * 재고 부족 시 예외 발생.
     *
     * @param items 구매 요청 정보 (상품명 → 구매 수량)
     * @return 총 금액 및 항목별 구매 상세가 담긴 Map
     */
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