package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.*;
import gdg.hongik.mission.domain.Item;
import gdg.hongik.mission.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")

/**
 * 관리자가 상품과 재고를 조작할 수 있는 Controller입니다.
 * 상품 등록, 재고 추가, 상품 삭제의 기능이 구현되어 있습니다
 */
public class AdminController {

    private final ItemService itemService;

    /**
     * Admin Controller 생성기
     * @param itemService Controller가 Service 계층을 사용합니다
     */
    public AdminController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * 새로운 상품을 추가하는 메서드입니다.
     * @param request Item Request DTO를 사용합니다.
     */
    @PostMapping("/add-item")
    public ResponseEntity<Void> addItem(@RequestBody ItemRequest request) {
        itemService.addItem(request.getName(), request.getPrice(), request.getStock());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 재고를 추가하는 메서드입니다.
     * @param request AddStockRequest DTO를 사용합니다.
     * @return Id, Name, Price, Stock을 반환합니다.
     */
    @PostMapping("/add-stock")
    public ItemResponse addStock(@RequestBody AddStockRequest request) {
        int updatedStock = itemService.addStock(request.getName(), request.getCount());
        Item item = itemService.getItemByName(request.getName());
        return new ItemResponse(item.getId(), item.getName(), item.getPrice(), updatedStock);
    }

    /**
     * 상품을 삭제하는 메서드입니다.
     * @param request DeleteItemsRequest DTO를 사용합니다.
     * @return
     */
    @PostMapping("/delete")
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