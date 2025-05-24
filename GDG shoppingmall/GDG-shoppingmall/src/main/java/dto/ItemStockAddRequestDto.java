package com.example.shoppingmall.dto;

public class ItemStockAddRequestDto {
    private String name;
    private int count;
    private String position;

    public ItemStockAddRequestDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
