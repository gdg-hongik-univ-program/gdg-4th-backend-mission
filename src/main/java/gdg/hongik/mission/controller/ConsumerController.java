package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.*;
import gdg.hongik.mission.domain.Item;
import gdg.hongik.mission.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 소비자(Consumer)용 컨트롤러
 */
@RestController
@RequestMapping("/api/consumer")
public class ConsumerController {

    private final ItemService itemService;

    public ConsumerController(ItemService itemService) {
        this.itemService = itemService;
    }

    // 상품 이름으로 검색
    @GetMapping("/item/{name}")
    public ItemResponse getItem(@PathVariable String name) {
        Item item = itemService.getItemByName(name);
        return new ItemResponse(item.getId(), item.getName(), item.getPrice(), item.getStock());
    }

    // 상품 구매 (장바구니) - 여러 상품 한 번에 구매
    @PostMapping("/purchase")
    public PurchaseResponse purchase(@RequestBody PurchaseRequest request) {
        // 실제 서비스 구현 시 DTO 변환, 에러처리 등 추가하면 좋음
        int totalPrice = itemService.purchaseItems(
                request.getItems().stream()
                        .map(i -> new ItemService.PurchaseItem(i.getName(), i.getCount()))
                        .collect(Collectors.toList())
        );
        // 구매 결과 DTO 만들어서 반환 (여기서는 단순 예시, 실제로는 구매 상품별 가격 계산 등 필요)
        List<PurchaseResponse.PurchasedItem> purchasedItems = request.getItems().stream()
                .map(i -> {
                    Item item = itemService.getItemByName(i.getName());
                    return new PurchaseResponse.PurchasedItem(
                            item.getName(),
                            i.getCount(),
                            item.getPrice() * i.getCount()
                    );
                })
                .collect(Collectors.toList());

        return new PurchaseResponse(totalPrice, purchasedItems);
    }
}