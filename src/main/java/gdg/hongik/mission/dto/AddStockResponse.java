package gdg.hongik.mission.dto;

public class AddStockResponse {
    private String name;
    private int stock;

    public AddStockResponse(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }
    public String getName() { return name; }
    public int getStock() { return stock; }
}
