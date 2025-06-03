package gdg.hongik.mission.order;

import gdg.hongik.mission.order.dto.BuyRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 구매 요청을 처리하는 컨트롤러 클래스입니다.
 */
@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 구매 요청 API.
     *
     * @param request 구매 요청 DTO
     * @return 구매 결과 및 메시지
     */
    @PostMapping("/buy")
    public ResponseEntity<?> buy(@RequestBody BuyRequestDTO request) {
        return ResponseEntity.ok(orderService.placeOrder(request));
    }
}
