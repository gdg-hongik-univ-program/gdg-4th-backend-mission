package com.example.shoppingmall.dto;

public class ItemResponseDto {
    private Long id;
    private String name;
    private int price;
    private int stock;

    public ItemResponseDto() {
    }

    public ItemResponseDto(Long id, String name, int price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }
}
