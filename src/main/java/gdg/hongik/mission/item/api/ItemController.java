package gdg.hongik.mission.item.api;

import gdg.hongik.mission.cart.domain.Cart;
import gdg.hongik.mission.item.DTO.req.BuyReqDTO;
import gdg.hongik.mission.item.DTO.res.BuyResDTO;
import gdg.hongik.mission.item.DTO.req.DeleteReqDTO;
import gdg.hongik.mission.item.DTO.req.DeleteReqDTO.ItemReq;
import gdg.hongik.mission.item.DTO.res.DeleteResDTO;
import gdg.hongik.mission.item.DTO.res.DeleteResDTO.ItemResDTO;
import gdg.hongik.mission.item.DTO.req.IncreaseReqDTO;
import gdg.hongik.mission.item.DTO.res.IncreaseResDTO;
import gdg.hongik.mission.item.DTO.req.RegisterReqDTO;
import gdg.hongik.mission.item.DTO.req.SearchReqDTO;
import gdg.hongik.mission.item.DTO.res.SearchResDTO;
import gdg.hongik.mission.item.domain.Item;
import gdg.hongik.mission.item.sevice.ItemAdminService;
import gdg.hongik.mission.item.sevice.ItemUserService;
import gdg.hongik.mission.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ItemController {
    private final ItemAdminService itemAdminService;
    private final ItemUserService itemUserService;

    // User API
    @GetMapping()
    public ResponseEntity<List<SearchResDTO>> getItems() {
        List<SearchResDTO> items = itemUserService.findAllItems().stream()
        .map(SearchResDTO::new).toList();
        return ResponseEntity.ok(items);
    }

    @PostMapping("search")
    public ResponseEntity<SearchResDTO> getItems(@RequestBody SearchReqDTO req) {
        String name = req.itemName();
        Item item = itemUserService.findItemByName(name);
        return ResponseEntity.ok(new SearchResDTO(item));
    }

    @PostMapping("buy")
    public ResponseEntity<BuyResDTO> buyItem(@RequestBody BuyReqDTO req) {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.info("▶ BuyReqDTO: {}", req);
        logger.info("▶ domainOrders: {}", req.toDomainOrders());

        Cart cart = new Cart(req.toDomainOrders());
        List<Item> receipt = itemUserService.buyItems(cart);
        int totalPrice = receipt.stream()
                .mapToInt(item -> item.calculatePrice(item.getQuantity())).sum();

        return ResponseEntity.ok(BuyResDTO.from(totalPrice, receipt));
    }

    // admin API
    @PostMapping("register")
    public ResponseEntity<Void> registerItem(@RequestBody RegisterReqDTO req) {
        User user = new User(req.userName(), req.position());
        itemAdminService.registerItem(new Item(req.itemName(), req.stock(), req.price()), user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("increase")
    public ResponseEntity<IncreaseResDTO> increaseItem(@RequestBody IncreaseReqDTO req) {
        User user = new User(req.userName(), req.position());
        Item item = itemAdminService.increaseItem(req.itemName(), req.count(), user);

        return ResponseEntity.ok(new IncreaseResDTO(item.getName(), item.getQuantity()));
    }

    @PostMapping("delete")
    public ResponseEntity<DeleteResDTO> deleteItem(@RequestBody DeleteReqDTO req) {
        User user = new User(req.userName(), req.position());
        List<String> names = req.items().stream().map(ItemReq::itemName).toList();
        itemAdminService.deleteItems(names, user);

        List<Item> allItems = itemAdminService.findAllItems(user);
        List<ItemResDTO> items = allItems.stream().map(item -> new ItemResDTO(item.getName(), item.getQuantity())).toList();

        return ResponseEntity.ok(new DeleteResDTO(items));
    }
}
