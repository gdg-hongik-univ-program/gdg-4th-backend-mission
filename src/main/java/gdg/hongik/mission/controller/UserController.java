package gdg.hongik.mission.controller;

import gdg.hongik.mission.service.InventoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class UserController {
    private final InventoryService service;

    public UserController(InventoryService service) {
        this.service = service;
    }

    // 재고 검색 (상품 상세 조회)
    @PostMapping("/search")
    public SearchResponse search(@RequestBody SearchRequest req) {
        return service.findProduct(req.getSearchName());
    }

    // 상품 구매
    @PostMapping("/purchase")
    public PurchaseResponse purchase(@RequestBody PurchaseRequest req) {
        return service.purchase(req.getName(), req.getItems());
    }

    // 예약하기
    @PostMapping("/reserve")
    public void reserve(@RequestBody ReserveRequest req) {
        service.reserve(req.getName(), req.getItems());
    }

    // 예약 취소하기
    @PostMapping("/cancel")
    public void cancelReserve(@RequestBody CancelReserveRequest req) {
        service.cancelReserve(req.getName(), req.getTarget());
    }
}
