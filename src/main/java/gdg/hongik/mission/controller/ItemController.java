package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.PurchaseRequest;
import gdg.hongik.mission.dto.RegisterItemRequest;
import gdg.hongik.mission.entity.Item;
import gdg.hongik.mission.entity.User;
import gdg.hongik.mission.service.AdminItemService;
import gdg.hongik.mission.service.ConsumerItemService;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/item")
public class ItemController {
    private final AdminItemService adminService;
    private final ConsumerItemService consumerService;

    public ItemController(AdminItemService adminService, ConsumerItemService consumerService) {
        this.adminService = adminService;
        this.consumerService = consumerService;
    }

    @GetMapping("/search")
    public Item search(@RequestParam String name) {
        return consumerService.findItem(name);
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterItemRequest req) {
        adminService.register(req);
    }

    @PostMapping("/addStock")
    public Map<String, Object> addStock(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        int count = (int) body.get("count");
        String roleStr = (String) body.get("position");
        Item updated = adminService.addStock(name, count, User.Role.valueOf(roleStr));
        return Map.of("name", updated.getName(), "stock", updated.getStock());
    }

    @PostMapping("/delete")
    public Map<String, Object> delete(@RequestBody Map<String, Object> body) {
        List<String> names = ((List<Map<String, String>>) body.get("items")).stream()
                .map(m -> m.get("name")).toList();
        String roleStr = (String) body.get("position");
        List<Map<String, Object>> result = adminService.deleteItems(names, User.Role.valueOf(roleStr));
        return Map.of("items", result);
    }

    @PostMapping("/purchase")
    public Map<String, Object> purchase(@RequestBody PurchaseRequest req) {
        return consumerService.purchase(req);
    }
}
