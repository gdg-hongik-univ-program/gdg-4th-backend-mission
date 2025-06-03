package gdg.hongik.mission.item;

import gdg.hongik.mission.item.dto.ItemRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 상품 관련 요청을 처리하는 컨트롤러 클래스입니다.
 * 상품 등록, 조회, 삭제 기능을 제공합니다.
 */
@RestController
@RequestMapping("/items")
@CrossOrigin(origins = "*")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * 새로운 상품을 등록합니다.
     *
     * @param request 등록할 상품의 이름, 가격, 재고를 담은 DTO
     * @return 등록된 상품 정보
     */
    @PostMapping("/register")
    public ResponseEntity<Item> registerItem(@RequestBody ItemRequestDTO request) {
        Item item = itemService.registerItem(request.getItemName(), request.getPrice(), request.getStock());
        return ResponseEntity.ok(item);
    }

    /**
     * 등록된 모든 상품을 조회합니다.
     *
     * @return 상품 목록
     */
    @GetMapping("/all")
    public ResponseEntity<List<Item>> findAll() {
        return ResponseEntity.ok(itemService.findAll());
    }

    /**
     * 특정 상품을 삭제합니다.
     *
     * @param name 삭제할 상품 이름
     * @return 삭제 완료 메시지
     */
    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteItem(@PathVariable String name) {
        itemService.deleteItem(name);
        return ResponseEntity.ok("삭제 완료");
    }
}
