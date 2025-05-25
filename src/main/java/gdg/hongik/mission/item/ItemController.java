package gdg.hongik.mission.item;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * 아이템을 검색합니다.
     *
     * @param request 요청 데이터 JSON을 Map으로 변환하여 request 객체로 변환
     * @return Item 객체를 JSON 형태로 반환합니다.
     */
    @PostMapping("/search")
    public ResponseEntity<Item> search(@RequestBody Map<String, String> request) {
        // 검색 요청으로 들어오는 객체는 key와 value 값 모두 string이므로 Map<String,String>으로 객체화
        // ex) name = "apple"
        String itemName = (String)request.get("itemName");
        return ResponseEntity.ok(itemService.searchByName(itemName));
    }

    /**
     * 아이템을 구매합니다.
     *
     * @param request 요청 데이터 JSON을 Map으로 변환하여 request 객체로 변환
     * @return 상태코드와 구입한 아이템의 정보(이름, 가격, 남은 수량)를 JSON 형태로 반환합니다.
     */
    @PostMapping("/buy")
    public ResponseEntity<Map<String,Object>> buy(@RequestBody Map<String, Object> request) {
        // 구매 요청은 다양한 형태를 담고 있을 수 있으므로 Map<String, Object>로 객체화
        // ex) name = "apple", quantity = "3"
        String name= (String)request.get("name");
        String position = (String)request.get("position");
        List<Map<String, Object>> items = (List<Map<String, Object>>)request.get("items");
        return ResponseEntity.ok(itemService.buy(name, position, items));
    }

    /**
     * 아이템 재고를 추가합니다.
     *
     * @param request 요청 데이터 JSON을 Map으로 변환하여 request 객체로 변환
     * @return 상태코드와 추가된 Item 객체를 JSON 형태로 반환합니다.
     */
    @PostMapping("/add")
    public ResponseEntity<Item> addStock (@RequestBody Map<String, Object> request) {
        String name = (String)request.get("name");
        String position = (String)request.get("position");
        String itemName = (String)request.get("itemName");
        int count = (int) request.get("count");
        return ResponseEntity.ok(itemService.addStock(name, position, itemName, count));
    }

    /**
     * 아이템을 삭제합니다.
     *
     * @param request 요청 데이터 JSON을 Map으로 변환하여 request 객체로 변환
     * @return 상태 코드와 함께 삭제 후 남아있는 아이템을 리스트로 만들어 JSON 형태로 반환합니다.
     */
    @PostMapping("/delete")
    public ResponseEntity<Map<String,Object>> delete(@RequestBody Map<String, Object> request) {
        String name = (String)request.get("name");
        String position = (String)request.get("position");
        List<Map<String,String>> items = (List<Map<String,String>>)request.get("items");
        List<Map<String, Object>> remaining = itemService.deleteItem(name, position, items);
        Map<String, Object> result = new HashMap<>();
        result.put("items", remaining);
        return ResponseEntity.ok(result);
    }

    /**
     * 아이템을 새로 등록합니다.
     *
     * @param request 요청 데이터 JSON을 Map으로 변환하여 request 객체로 변환
     * @return 상태 코드와 "상품 등록 완료"를 반환합니다.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, Object> request) {
        String name = (String)request.get("name");
        String position = (String)request.get("position");
        String itemName = (String)request.get("itemName");
        int price = (int) request.get("price");
        int stock = (int) request.get("stock");
        itemService.registerItem(name, position, itemName, price, stock);
        return ResponseEntity.ok("상품 등록 완료");
    }
}
