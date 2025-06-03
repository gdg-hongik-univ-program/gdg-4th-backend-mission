

// 18. Controller - ItemController.java (JavaDoc & Swagger 포함 + record DTO 반영)
package gdg.hongik.mission.controller;

import gdg.hongik.mission.DTO.PurchaseRequest;
import gdg.hongik.mission.DTO.PurchaseRequest.ItemOrder;
import gdg.hongik.mission.DTO.RegisterItemRequest;
import gdg.hongik.mission.entity.Item;
import gdg.hongik.mission.entity.User;
import gdg.hongik.mission.service.AdminItemService;
import gdg.hongik.mission.service.ConsumerItemService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
        import java.util.*;

/**
 * 상품 관련 요청을 처리하는 컨트롤러입니다.
 * 역할에 따라 상품 검색, 등록, 삭제, 구매 등의 기능을 제공합니다.
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    private final AdminItemService adminService;
    private final ConsumerItemService consumerService;

    public ItemController(AdminItemService adminService, ConsumerItemService consumerService) {
        this.adminService = adminService;
        this.consumerService = consumerService;
    }

    /**
     * 상품 이름으로 재고를 조회합니다.
     * @param name 검색할 상품 이름
     * @return 상품 정보
     */
    @Operation(summary = "상품 조회", description = "상품 이름으로 재고를 조회합니다.")
    @GetMapping("/search")
    public Item search(@RequestParam String name) {
        return consumerService.findItem(name);
    }

    /**
     * 새 상품을 등록합니다. 관리자만 호출할 수 있습니다.
     * @param req 상품 등록 요청 정보
     */
    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다. 관리자만 사용할 수 있습니다.")
    @PostMapping("/register")
    public void register(@RequestBody RegisterItemRequest req) {
        adminService.register(req);
    }

    /**
     * 기존 상품에 재고를 추가합니다.
     * @param body 상품 이름, 수량, 관리자 권한 포함
     * @return 추가 후 재고 정보
     */
    @Operation(summary = "재고 추가", description = "상품 이름과 수량을 입력하여 재고를 추가합니다.")
    @PostMapping("/addStock")
    public Map<String, Object> addStock(@RequestBody Map<String, Object> body) {
        String name = (String) body.get("name");
        int count = (int) body.get("count");
        String roleStr = (String) body.get("position");
        Item updated = adminService.addStock(name, count, User.Role.valueOf(roleStr));
        return Map.of("name", updated.getName(), "stock", updated.getStock());
    }

    /**
     * 상품을 삭제합니다. 관리자만 사용 가능합니다.
     * @param body 삭제할 상품 목록과 관리자 권한
     * @return 삭제 후 남은 상품 목록
     */
    @Operation(summary = "상품 삭제", description = "관리자가 상품을 삭제합니다.")
    @PostMapping("/delete")
    public Map<String, Object> delete(@RequestBody Map<String, Object> body) {
        List<String> names = ((List<Map<String, String>>) body.get("items"))
                .stream().map(m -> m.get("name")).toList();
        String roleStr = (String) body.get("position");
        List<Map<String, Object>> result = adminService.deleteItems(names, User.Role.valueOf(roleStr));
        return Map.of("items", result);
    }

    /**
     * 소비자가 상품을 구매합니다.
     * @param req 구매 요청 정보
     * @return 총 구매 금액 및 항목별 구매 정보
     */
    @Operation(summary = "상품 구매", description = "소비자가 장바구니에 담은 상품을 구매합니다.")
    @PostMapping("/purchase")
    public Map<String, Object> purchase(@RequestBody PurchaseRequest req) {
        return consumerService.purchase(req);
    }
}
