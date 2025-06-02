package gdg.hongik.mission.Repository;

import gdg.hongik.mission.Domain.ItemInfo;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.Scanner;

@Repository
public class ItemRepository {
    Scanner scanner = new Scanner(System.in);
    private String ItemName;
    private final ItemInfo itemInfo;

    public ItemRepository(ItemInfo itemInfo) {
        this.itemInfo = itemInfo;
    }

/*    public String inputItem(){
        ItemName = scanner.nextLine();
        return ItemName;
    }
*/
}