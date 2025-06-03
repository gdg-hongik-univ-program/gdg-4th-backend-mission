package gdg.hongik.mission.order;

import gdg.hongik.mission.item.Item;
import gdg.hongik.mission.item.ItemService;
import gdg.hongik.mission.user.User;
import gdg.hongik.mission.user.UserService;
import gdg.hongik.mission.order.dto.BuyRequestDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 주문(구매) 로직을 처리하는 서비스 클래스입니다.
 */
@Service
public class OrderService {

    private final UserService userService;
    private final ItemService itemService;
    private final OrderRepository orderRepository;

    public OrderService(UserService userService, ItemService itemService, OrderRepository orderRepository) {
        this.userService = userService;
        this.itemService = itemService;
        this.orderRepository = orderRepository;
    }

    /**
     * 구매 요청을 처리합니다.
     *
     * @param request 구매 요청 DTO
     * @return 구매 결과 및 총 금액 정보
     */
    public Map<String, Object> placeOrder(BuyRequestDTO request) {
        User user = userService.findByName(request.getName());
        if (!"CONSUMER".equalsIgnoreCase(user.getPosition())) {
            throw new IllegalArgumentException("CONSUMER만 구매 가능합니다.");
        }

        Order order = new Order(user);
        int totalPrice = 0;

        for (BuyRequestDTO.ItemOrderDTO itemRequest : request.getItems()) {
            Item item = itemService.findByName(itemRequest.getName());
            item.decreaseStock(itemRequest.getCount());
            OrderItem orderItem = new OrderItem(item, itemRequest.getCount());
            order.addOrderItem(orderItem);
            totalPrice += item.getPrice() * itemRequest.getCount();
        }

        orderRepository.save(order);

        Map<String, Object> result = new HashMap<>();
        result.put("totalPrice", totalPrice);
        result.put("message", "구매 완료");
        return result;
    }
}
