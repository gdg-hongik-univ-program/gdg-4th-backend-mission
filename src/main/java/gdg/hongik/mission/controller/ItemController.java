package gdg.hongik.mission.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import gdg.hongik.mission.dto.ItemDto;
import gdg.hongik.mission.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "Item API", description = "상품 등록/조회/삭제/구매 관련 API")
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(summary = "상품 등록", description = "관리자 권한으로 새로운 상품을 등록합니다.")
    @PostMapping("/register")
    public void registerItem(@RequestBody Map<String, Object> request) {
        itemService.registerItem(
                (String) request.get("name"),
                (Integer) request.get("price"),
                (Integer) request.get("stock"),
                (String) request.get("position")
        );
    }

    @Operation(summary = "상품 조회", description = "상품 이름으로 정보를 조회합니다.")
    @GetMapping("/find")
    public ItemDto findItem(@RequestParam String name) {
        return itemService.findItem(name);
    }

    @Operation(summary = "재고 추가", description = "기존 상품에 재고를 추가합니다. 관리자 권한 필요.")
    @PostMapping("/add-stock")
    public ItemDto addStock(@RequestBody Map<String, Object> request) {
        return itemService.addStock(
                (String) request.get("name"),
                (Integer) request.get("count"),
                (String) request.get("position")
        );
    }

    @Operation(summary = "상품 삭제", description = "여러 개의 상품을 삭제합니다. 관리자 권한 필요.")
    @PostMapping("/delete")
    public List<ItemDto> deleteItems(@RequestBody Map<String, Object> request) {
        List<Map<String, String>> items = (List<Map<String, String>>) request.get("items");
        List<String> names = items.stream().map(map -> map.get("name")).toList();
        String position = (String) request.get("position");
        return itemService.deleteItems(names, position);
    }

    @Operation(summary = "상품 구매", description = "장바구니에 담긴 상품을 구매합니다.")
    @PostMapping("/purchase")
    public Map<String, Object> purchaseItems(@RequestBody Map<String, Object> request) {
        return itemService.purchaseItems((List<Map<String, Object>>) request.get("items"));
    }
}
