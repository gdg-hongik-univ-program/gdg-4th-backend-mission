# 3주차 WIL 적는 곳
1. 이번 주 배운 것

- JavaDoc



- Swagger



2. 배운 내용

- JavaDoc : Java 소스 코드 내에 문서화 주석을 생성하여, HTML형식의 API문서를 자동으로 만들어 주는 도구, /** 로 시작하고 각 줄에 *을 작성하고 */로 끝맺는다.


JavaDoc 태그들


/**
* 상품 등록, 조회, 재고 추가, 삭제, 구매 기능 제공공
  */



package gdg.hongik.mission.controller;

import gdg.hongik.mission.dto.ItemDto;
import gdg.hongik.mission.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * 새 상품 등록
     *
     * @param request name, price, stock, position
     */

    @PostMapping("/register")
    public void registerItem(@RequestBody Map<String, Object> request) {
        itemService.registerItem(
                (String) request.get("name"),
                (Integer) request.get("price"),
                (Integer) request.get("stock"),
                (String) request.get("position")
        );
    }
   
    /**
     * 상품 이름으로 상품 조회
     *
     * @param name 조회할 상품 이름
     * @return 상품 정보 DTO
     */
    @GetMapping("/find")
    public ItemDto findItem(@RequestParam String name) {
        return itemService.findItem(name);
    }
    /**
     * 상품 재고 추가
     *
     * @param request name, count, position
     * @return 추가 후 상품 정보 DTO
     */

    @PostMapping("/add-stock")
    public ItemDto addStock(@RequestBody Map<String, Object> request) {
        return itemService.addStock(
                (String) request.get("name"),
                (Integer) request.get("count"),
                (String) request.get("position")
        );
    }
    /**
     * 여러 상품 삭제
     * @param request item(name 리스트), position
     * @return 삭제 후 남아있는 상품 목록록
     */
    @PostMapping("/delete")
    public List<ItemDto> deleteItems(@RequestBody Map<String, Object> request) {
        List<Map<String, String>> items = (List<Map<String, String>>) request.get("items");
        List<String> names = items.stream().map(map -> map.get("name")).toList();
        String position = (String) request.get("position");
        return itemService.deleteItems(names, position);
    }

    /**
     * 상품 구매 요청 처리
     * @param request 구매 요청 상품 리스트
     * @return 총 구매 금액 및 상세 정보
     */
    @PostMapping("/purchase")
    public Map<String, Object> purchaseItems(@RequestBody Map<String, Object> request) {
        return itemService.purchaseItems((List<Map<String, Object>>) request.get("items"));
    }
}
위 형태로 ItemController.java 코드에 JavaDocs 추가







- swagger : RESTful API를 편히라게 문서화하는데 도움을 주는 오픈 소스 프레임워크



package gdg.hongik.mission.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import gdg.hongik.mission.dto.ItemDto;
import gdg.hongik.mission.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Tag(name = "Item API", description = "상품 등록/조회/삭제/구매 관련 API")
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(summary = "상품 등록", description = "관리자 권한으로 새로운 상품을 등록합니다.")
    @PostMapping("/register")
    public void registerItem(@RequestBody Map<String, Object> request) {
        itemService.registerItem(
                (String) request.get("name"),
                (Integer) request.get("price"),
                (Integer) request.get("stock"),
                (String) request.get("position")
        );
    }

    @Operation(summary = "상품 조회", description = "상품 이름으로 정보를 조회합니다.")
    @GetMapping("/find")
    public ItemDto findItem(@RequestParam String name) {
        return itemService.findItem(name);
    }

    @Operation(summary = "재고 추가", description = "기존 상품에 재고를 추가합니다. 관리자 권한 필요.")
    @PostMapping("/add-stock")
    public ItemDto addStock(@RequestBody Map<String, Object> request) {
        return itemService.addStock(
                (String) request.get("name"),
                (Integer) request.get("count"),
                (String) request.get("position")
        );
    }

    @Operation(summary = "상품 삭제", description = "여러 개의 상품을 삭제합니다. 관리자 권한 필요.")
    @PostMapping("/delete")
    public List<ItemDto> deleteItems(@RequestBody Map<String, Object> request) {
        List<Map<String, String>> items = (List<Map<String, String>>) request.get("items");
        List<String> names = items.stream().map(map -> map.get("name")).toList();
        String position = (String) request.get("position");
        return itemService.deleteItems(names, position);
    }

    @Operation(summary = "상품 구매", description = "장바구니에 담긴 상품을 구매합니다.")
    @PostMapping("/purchase")
    public Map<String, Object> purchaseItems(@RequestBody Map<String, Object> request) {
        return itemService.purchaseItems((List<Map<String, Object>>) request.get("items"));
    }
}
위의 형식으로 ItemController.java에 Swagger 추가



3. 느낀 점 ,공부 방향성

JavaDocs와 Swagger에 대해 공부하고 이를 직접 내 코드에 추가해 보았다. 그러면서 내가 작성한 코드하나하나 살펴보며 정확히 어떤 역할을 하는지 이해하는 시간을 가졌다. 아직 프론트엔드와 api연결을 직접해보지 않아 공부한 내용이 완전히 이해가 되지 않았지만 다음 프로젝트 세션을 통해 이해해봐야겠다