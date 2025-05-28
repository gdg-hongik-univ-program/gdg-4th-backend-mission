package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.*;
import gdg.hongik.mission.domain.Item;
import gdg.hongik.mission.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/consumer")
/**
 * 소비자가 재고 검색, 구매를 할 수 있는 Controller입니다.
 */
public class ConsumerController {

    private final ItemService itemService;

    /**
     * Consumer Controller 생성기
     * @param itemService Controller가 서비스 계층을 사용합니다.
     */
    public ConsumerController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * 재고 검색을 하는 메서드 입니다.
     * @param name 검색할 재고의 이름을 입력합니다
     * @return  Id, Name, Price, Stock을 반환합니다.
     */
    @GetMapping("/item/{name}")
    public ItemResponse getItem(@PathVariable("name") String name) {
        Item item = itemService.getItemByName(name);
        return new ItemResponse(item.getId(), item.getName(), item.getPrice(), item.getStock());
    }

    /**
     * 재고 구매를 하는 메서드 입니다
     * @param request ItemName, Count(구매 개수)를 포함합니다.
     * @return
     */
    @PostMapping("/purchase")
    public PurchaseResponse purchase(@RequestBody PurchaseRequest request) {
        int totalPrice = itemService.purchaseItems(
                request.getItems().stream()
                        .map(i -> new ItemService.PurchaseItem(i.getName(), i.getCount()))
                        .collect(Collectors.toList())
        );
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