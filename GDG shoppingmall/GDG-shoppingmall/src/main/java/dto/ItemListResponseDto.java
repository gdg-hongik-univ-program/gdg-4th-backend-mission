package com.example.shoppingmall.dto;

import java.util.List;

public class ItemListResponseDto {
    private List<ItemDto> items;

    public ItemListResponseDto() {
    }

    public ItemListResponseDto(List<ItemDto> items) {
        this.items = items;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }

    public static class ItemDto {
        private String name;
        private int stock;

        public ItemDto() {
        }

        public ItemDto(String name, int stock) {
            this.name = name;
            this.stock = stock;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }
    }
}
