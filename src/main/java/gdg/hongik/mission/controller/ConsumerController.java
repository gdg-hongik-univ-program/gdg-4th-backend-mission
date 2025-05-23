package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.*;
import gdg.hongik.mission.domain.Item;
import gdg.hongik.mission.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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