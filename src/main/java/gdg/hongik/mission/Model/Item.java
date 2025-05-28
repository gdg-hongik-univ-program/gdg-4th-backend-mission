package gdg.hongik.mission.Model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ITEMS")
@Builder

public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;
    private String itemName;
    private int itemPrice;
    private int itemStock;

    public Item(int itemId, String itemName, int itemPrice, int itemStock){
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemStock = itemStock;
    }
    public int getId() {return itemId;}
    public String getName() {return itemName;}
    public int getPrice() {return itemPrice;}
    public int getStock() {return itemStock;}
    public void setStock(int itemStock) {this.itemStock = itemStock;}
}

