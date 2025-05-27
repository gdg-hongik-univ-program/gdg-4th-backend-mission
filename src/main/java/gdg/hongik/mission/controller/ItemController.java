package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.ItemDto;
import gdg.hongik.mission.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/register")
    public void registerItem(@RequestBody Map<String, Object> request) {
        itemService.registerItem(
                (String) request.get("name"),
                (Integer) request.get("price"),
                (Integer) request.get("stock"),
                (String) request.get("position")
        );
    }

    @GetMapping("/find")
    public ItemDto findItem(@RequestParam String name) {
        return itemService.findItem(name);
    }

    @PostMapping("/add-stock")
    public ItemDto addStock(@RequestBody Map<String, Object> request) {
        return itemService.addStock(
                (String) request.get("name"),
                (Integer) request.get("count"),
                (String) request.get("position")
        );
    }

    @PostMapping("/delete")
    public List<ItemDto> deleteItems(@RequestBody Map<String, Object> request) {
        List<Map<String, String>> items = (List<Map<String, String>>) request.get("items");
        List<String> names = items.stream().map(map -> map.get("name")).toList();
        String position = (String) request.get("position");
        return itemService.deleteItems(names, position);
    }

    @PostMapping("/purchase")
    public Map<String, Object> purchaseItems(@RequestBody Map<String, Object> request) {
        return itemService.purchaseItems((List<Map<String, Object>>) request.get("items"));
    }
}
