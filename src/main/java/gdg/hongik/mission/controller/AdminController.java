package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.*;
import gdg.hongik.mission.domain.Item;
import gdg.hongik.mission.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 관리자(Admin)용 컨트롤러
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ItemService itemService;

    public AdminController(ItemService itemService) {
        this.itemService = itemService;
    }

    // 상품 등록
    @PostMapping("/add-item")
    public void addItem(@RequestBody ItemRequest request) {
        itemService.addItem(request.getName(), request.getPrice(), request.getStock());
    }

    // 재고 추가
    @PostMapping("/add-stock")
    public ItemResponse addStock(@RequestBody AddStockRequest request) {
        int updatedStock = itemService.addStock(request.getName(), request.getCount());
        Item item = itemService.getItemByName(request.getName());
        return new ItemResponse(item.getId(), item.getName(), item.getPrice(), updatedStock);
    }

    // 상품 삭제
    @PostMapping("/delete-items")
    public RemainingItemsResponse deleteItems(@RequestBody DeleteItemsRequest request) {
        itemService.deleteItems(request.getNames());
        // 남은 상품 목록 반환
        List<Item> items = itemService.getAllItems();
        List<ItemResponse> responses = items.stream()
                .map(i -> new ItemResponse(i.getId(), i.getName(), i.getPrice(), i.getStock()))
                .collect(Collectors.toList());
        return new RemainingItemsResponse(responses);
    }
}
