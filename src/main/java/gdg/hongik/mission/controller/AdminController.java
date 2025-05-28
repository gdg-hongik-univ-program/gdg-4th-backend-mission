package gdg.hongik.mission.controller;

import gdg.hongik.mission.model.Position;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final InventoryService service;

    public AdminController(InventoryService service) {
        this.service = service;
    }

    private void requireAdmin(Position pos) {
        if (pos != Position.ADMIN) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Admin only");
        }
    }

    // 상품 등록
    @PostMapping("/s")
    public void register(@RequestBody RegisterRequest req) {
        requireAdmin(req.getPosition());
        service.addNewProduct(req.getName(), req.getPrice(), req.getStock());
    }

    // 재고 추가
    @PatchMapping("/items/{name}/stock")
    public AddStockResponse addStock(
            @PathVariable String name,
            @RequestBody AddStockRequest req
    ) {
        requireAdmin(req.getPosition());
        return service.addStock(name, req.getCount());
    }

    // 상품 삭제
    @DeleteMapping("/items")
    public DeleteResponse delete(@RequestBody DeleteRequest req) {
        requireAdmin(req.getPosition());
        return service.deleteItems(req.getItems());
    }

    // 전체 예약 목록 조회
    @GetMapping("/reservations")
    public ReservationListResponse listReservations(@RequestBody BaseRequest req) {
        requireAdmin(req.getPosition());
        return new ReservationListResponse(service.listReservations());
    }

    // 특정 사용자 구매 기록 조회
    @PostMapping("/records")
    public RecordResponse getRecords(@RequestBody RecordRequest req) {
        requireAdmin(req.getPosition());
        return new RecordResponse(service.getPurchaseRecords(req.getTarget()));
    }

    // 구매 통계 조회
    @PostMapping("/statistics")
    public StatisticsResponse getStatistics(@RequestBody RecordRequest req) {
        requireAdmin(req.getPosition());
        return service.getStatistics(req.getTarget());
    }
}

}
