package gdg.hongik.mission.dto;

public class AddStockRequest {
    private String name;
    private int count;

    public AddStockRequest() {}

    /**
     * 재고를 추가하는 메서드
     * @param name 상품명
     * @param count 재고량
     */
    public AddStockRequest(String name, int count) {
        this.name = name;
        this.count = count;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
}