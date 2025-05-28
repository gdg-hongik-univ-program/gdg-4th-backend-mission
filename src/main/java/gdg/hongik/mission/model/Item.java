package gdg.hongik.mission.model;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class Item {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stock;

    protected Item(){}

    public Item(int id, String name, int price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {return id;}
    public String getName() {return name;}
    public int getPrice() {return price;}
    public int getStock() { return stock;}

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
