package gdg.hongik.mission.item.api;

import gdg.hongik.mission.ItemOrder.domain.ItemOrder;
import gdg.hongik.mission.cart.domain.Cart;
import gdg.hongik.mission.item.DTO.BuyReqDTO;
import gdg.hongik.mission.item.DTO.BuyResDTO;
import gdg.hongik.mission.item.DTO.DeleteReqDTO;
import gdg.hongik.mission.item.DTO.DeleteReqDTO.ItemReq;
import gdg.hongik.mission.item.DTO.DeleteResDTO;
import gdg.hongik.mission.item.DTO.DeleteResDTO.ItemResDTO;
import gdg.hongik.mission.item.DTO.IncreaseReqDTO;
import gdg.hongik.mission.item.DTO.IncreaseResDTO;
import gdg.hongik.mission.item.DTO.RegisterReqDTO;
import gdg.hongik.mission.item.DTO.SearchReqDTO;
import gdg.hongik.mission.item.domain.Item;
import gdg.hongik.mission.item.sevice.ItemAdminService;
import gdg.hongik.mission.item.sevice.ItemUserService;
import gdg.hongik.mission.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemAdminService itemAdminService;
    private final ItemUserService itemUserService;

    // User API
    @PostMapping("search")
    public ResponseEntity<Item> getItems(@RequestBody SearchReqDTO req) {
        String name = req.itemName();
        Item item = itemUserService.findItemByName(name);
        return ResponseEntity.ok(item);
    }

    @PostMapping("buy")
    public ResponseEntity<BuyResDTO> buyItem(@RequestBody BuyReqDTO req) {
        Cart cart = new Cart(req.toDomainOrders());
        List<Item> receipt = itemUserService.buyItems(cart);
        int totalPrice = receipt.stream()
                .mapToInt(item -> item.calculatePrice(item.getQuantity())).sum();

        return ResponseEntity.ok(new BuyResDTO(totalPrice, receipt));
    }

    // admin API
    @PostMapping("register")
    public ResponseEntity<Void> registerItem(@RequestBody RegisterReqDTO req) {
        User user = new User(req.name(), req.position());
        itemAdminService.registerItem(new Item(req.itemName(), req.stock(), req.price()), user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("increase")
    public ResponseEntity<IncreaseResDTO> increaseItem(@RequestBody IncreaseReqDTO req) {
        User user = new User(req.name(), req.position());
        Item item = itemAdminService.increaseItem(req.itemName(), req.count(), user);

        return ResponseEntity.ok(new IncreaseResDTO(item.getName(), item.getQuantity()));
    }

    @PostMapping("delete")
    public ResponseEntity<DeleteResDTO> deleteItem(@RequestBody DeleteReqDTO req) {
        User user = new User(req.name(), req.position());
        List<String> names = req.items().stream().map(ItemReq::name).toList();
        itemAdminService.deleteItems(names, user);

        List<Item> allItems = itemAdminService.findAllItems(user);
        List<ItemResDTO> items = allItems.stream().map(item -> new ItemResDTO(item.getName(), item.getQuantity())).toList();

        return ResponseEntity.ok(new DeleteResDTO(items));
    }
}
