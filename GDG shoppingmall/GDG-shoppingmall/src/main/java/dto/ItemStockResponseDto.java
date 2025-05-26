package com.example.shoppingmall.dto;

public class ItemStockResponseDto {
    private String name;
    private int stock;

    public ItemStockResponseDto() {
    }

    public ItemStockResponseDto(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }
}

