package gdg.hongik.mission.dto;

// 상품 등록(관리자용)
public class ItemRequest {
    private String name;
    private int price;
    private int stock;

    public ItemRequest() {} // 기본 생성자

    public ItemRequest(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Getter/Setter
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}