package gdg.hongik.mission.Domain;

import org.springframework.stereotype.Component;

@Component
public class ItemInfo {
    private String name;
    private String Itemid;
    private String price;
    private String stock;

    //getter
    public String getName() {
        return name;
    }
    public String getItemid() {
        return Itemid;
    }
    public String getPrice() {
        return price;
    }
    public String getStock() {
        return stock;
    }

    //setter
    public void setName(String name) {
        this.name = name;
    }
    public void setItemid(String itemid) {
        Itemid = itemid;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setStock(String stock) {
        this.stock = stock;
    }
}
