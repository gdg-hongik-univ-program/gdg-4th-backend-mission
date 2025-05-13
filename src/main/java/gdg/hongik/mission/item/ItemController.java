package gdg.hongik.mission.item;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/search")
    public ResponseEntity<Item> search(@RequestBody Map<String, String> request) {
        String itemName = (String)request.get("itemName");
        return ResponseEntity.ok(itemService.searchByName(itemName));
    }

    @PostMapping("/buy")
    public ResponseEntity<Map<String,Object>> buy(@RequestBody Map<String, Object> request) {
        String name= (String)request.get("name");
        String position = (String)request.get("position");
        List<Map<String, Object>> items = (List<Map<String, Object>>)request.get("items");
        return ResponseEntity.ok(itemService.buy(name, position, items));
    }

    @PostMapping("/add")
    public ResponseEntity<Item> addStock (@RequestBody Map<String, Object> request) {
        String name = (String)request.get("name");
        String position = (String)request.get("position");
        String itemName = (String)request.get("itemName");
        int count = (int) request.get("count");
        return ResponseEntity.ok(itemService.addStock(name, position, itemName, count));
    }

    @PostMapping("/delete")
    public ResponseEntity<Map<String,Object>> delete(@RequestBody Map<String, Object> request) {
        String name = (String)request.get("name");
        String position = (String)request.get("position");
        List<Map<String,String>> items = (List<Map<String,String>>)request.get("items");
        List<Map<String, Object>> remaining = itemService.deleteItem(name, position, items);
        Map<String, Object> result = new HashMap<>();
        result.put("items", remaining);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, Object> request) {
        String name = (String)request.get("name");
        String position = (String)request.get("position");
        String itemName = (String)request.get("itemName");
        int price = (int) request.get("price");
        int stock = (int) request.get("stock");
        itemService.registerItem(name, position, itemName, price, stock);
        return ResponseEntity.ok("상품 등록 완료");
    }
}
