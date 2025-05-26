package com.example.shoppingmall.controller;

import com.example.shoppingmall.dto.*;
import com.example.shoppingmall.model.Item;
import com.example.shoppingmall.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/search")
    public ResponseEntity<ItemResponseDto> searchItem(@RequestParam String name) {
        Item item = itemService.findItemByName(name);
        ItemResponseDto response = new ItemResponseDto(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getStock()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerItem(@RequestBody ItemRegisterRequestDto request) {
        itemService.registerItem(
                request.getName(),
                request.getPrice(),
                request.getStock(),
                request.getPosition()
        );
        return ResponseEntity.ok("상품이 등록되었습니다.");
    }

    @PostMapping("/add-stock")
    public ResponseEntity<ItemStockResponseDto> addStock(@RequestBody ItemStockAddRequestDto request) {
        Item item = itemService.addStock(
                request.getName(),
                request.getCount(),
                request.getPosition()
        );
        ItemStockResponseDto response = new ItemStockResponseDto(item.getName(), item.getStock());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete")
    public ResponseEntity<ItemListResponseDto> deleteItems(@RequestBody ItemDeleteRequestDto request) {
        List<String> namesToDelete = request.getItems().stream()
                .map(ItemDeleteRequestDto.ItemNameDto::getName)
                .collect(Collectors.toList());

        List<Item> remainingItems = itemService.deleteItems(namesToDelete, request.getPosition());

        List<ItemListResponseDto.ItemDto> responseItems = remainingItems.stream()
                .map(item -> new ItemListResponseDto.ItemDto(item.getName(), item.getStock()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(new ItemListResponseDto(responseItems));
    }
}
