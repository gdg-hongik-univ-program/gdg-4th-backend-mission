package com.example.shoppingmall.dto;

import java.util.List;

public class ItemDeleteRequestDto {

    private List<ItemNameDto> items;
    private String position;

    // 기본 생성자
    public ItemDeleteRequestDto() {
    }

    public List<ItemNameDto> getItems() {
        return items;
    }

    public void setItems(List<ItemNameDto> items) {
        this.items = items;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public static class ItemNameDto {
        private String name;

        public ItemNameDto() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
