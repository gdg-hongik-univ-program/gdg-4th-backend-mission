package gdg.hongik.mission.controller;

import gdg.hongik.mission.model.Item;
import gdg.hongik.mission.model.Position;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final InventoryService service;

    private void requireAdmin(Position pos) {
        if (pos != Position.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin only");
        }
    }

    // --- 소비자·관리자 공통: 재고 조회 ---
    @PostMapping("/search")
    public SearchResponse search(@RequestBody SearchRequest req) {
        // position 체크는 생략(관리자는 조회 가능, 소비자도 조회 가능)
        return service.findProduct(req.getSearchName());
    }

    // --- 소비자·관리자 공통: 구매 ---
    @PostMapping("/purchase")
    public PurchaseResponse purchase(@RequestBody PurchaseRequest req) {
        return service.purchase(req.getName(), req.getItems());
    }

    // --- 관리자: 상품 등록 ---
    @PostMapping
    public void register(@RequestBody RegisterRequest req) {
        requireAdmin(req.getPosition());
        service.addNewProduct(req.getName(), req.getPrice(), req.getStock());
    }

    // --- 관리자: 재고 추가 ---
    @PostMapping("/stock")
    public AddStockResponse addStock(
            @PathVariable String itemName,
            @RequestBody AddStockRequest req
    ) {
        requireAdmin(req.getPosition());
        return service.addStock(itemName, req.getCount());
    }

    // --- 관리자: 상품 삭제 ---
    @PostMapping("/delete")
    public DeleteResponse delete(@RequestBody DeleteRequest req) {
        requireAdmin(req.getPosition());
        return service.deleteItems(req.getItems());
    }
}