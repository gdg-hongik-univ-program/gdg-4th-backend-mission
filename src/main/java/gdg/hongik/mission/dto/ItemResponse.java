package gdg.hongik.mission.dto;

public class ItemResponse {
    private Long id;
    private String name;
    private int price;
    private int stock;

    public ItemResponse() {}

    /**
     * 재고 검색 메서드
     * @param id
     * @param name
     * @param price
     * @param stock
     */
    public ItemResponse(Long id, String name, int price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public int getPrice() { return price; }
    public int getStock() { return stock; }
}