package com.example.shopbackend.controller;

import com.example.shopbackend.domain.Item;
import com.example.shopbackend.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * 상품 등록, 조회, 재고 추가, 삭제, 구매를 처리하는 컨트롤러입니다.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    private void assertAdmin(String position) {
        if (!"ADMIN".equals(position)) {
            throw new IllegalArgumentException("관리자만 사용할 수 있는 기능입니다.");
        }
    }

    /**
     * 새로운 상품을 등록합니다. (관리자 전용)
     *
     * @param req {"position": "ADMIN", "item": {"name": "apple", "price": 1000, "stock": 100}}
     * @return 등록된 상품 정보
     */
    @Operation(
            summary = "상품 등록 (관리자)",
            description = "position이 'ADMIN'인 사용자가 새로운 상품을 등록합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "object"),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"name\": \"관리자\",\n" +
                                    "  \"position\": \"ADMIN\",\n" +
                                    "  \"item\": {\n" +
                                    "    \"name\": \"apple\",\n" +
                                    "    \"price\": 1000,\n" +
                                    "    \"stock\": 50\n" +
                                    "  }\n" +
                                    "}")
                    )
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "등록된 상품 정보",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n" +
                            "  \"id\": 1,\n" +
                            "  \"name\": \"apple\",\n" +
                            "  \"price\": 1000,\n" +
                            "  \"stock\": 50\n" +
                            "}")
            )
    )
    @PostMapping
    public Item registerItem(@RequestBody Map<String, Object> req) {
        String position = (String) req.get("position");
        assertAdmin(position);

        Map<String, Object> itemData = (Map<String, Object>) req.get("item");
        Item item = new Item();
        item.setName((String) itemData.get("name"));
        item.setPrice((int) itemData.get("price"));
        item.setStock((int) itemData.get("stock"));

        return itemService.registerItem(item);
    }

    /**
     * 상품을 이름으로 조회합니다.
     *
     * @param name 상품 이름
     * @return 상품 ID, 이름, 가격, 재고
     */
    @Operation(
            summary = "상품 조회",
            description = "등록된 상품 중 이름으로 조회합니다."
    )
    @Parameter(name = "name", description = "조회할 상품 이름", required = true)
    @ApiResponse(
            responseCode = "200",
            description = "조회된 상품 정보",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n" +
                            "  \"id\": 1,\n" +
                            "  \"name\": \"apple\",\n" +
                            "  \"price\": 1000,\n" +
                            "  \"stock\": 50\n" +
                            "}")
            )
    )
    @GetMapping("/{name}")
    public Item getItem(@PathVariable String name) {
        return itemService.getItemByName(name);
    }

    /**
     * 상품 재고를 추가합니다. (관리자 전용)
     *
     * @param req {"position": "ADMIN", "name": "apple", "count": 20}
     * @return 재고 추가 후 상품 정보
     */
    @Operation(
            summary = "재고 추가 (관리자)",
            description = "기존 상품에 재고를 추가합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "object"),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"name\": \"apple\",\n" +
                                    "  \"count\": 20,\n" +
                                    "  \"position\": \"ADMIN\" \n" +
                                    "}")
                    )
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "재고 추가된 상품 반환",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n" +
                            "  \"id\": 1,\n" +
                            "  \"name\": \"apple\",\n" +
                            "  \"price\": 1000, \n" +
                            "  \"stock\": 70\n" +
                            "}")
            )
    )
    @PostMapping("/add-stock")
    public Item addStock(@RequestBody Map<String, Object> req) {
        String position = (String) req.get("position");
        assertAdmin(position);

        String name = (String) req.get("name");
        int count = (int) req.get("count");

        return itemService.addStock(name, count);
    }

    /**
     * 여러 상품을 삭제합니다. (관리자 전용)
     *
     * @param req {"position": "ADMIN", "items": [{"name": "apple"}, {"name": "banana"}]}
     */
    @Operation(
            summary = "상품 삭제 (관리자)",
            description = "입력된 상품 목록을 삭제합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "object"),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"name\": \"관리자\",\n" +
                                    "  \"position\": \"ADMIN\",\n" +
                                    "  \"items\": [\n" +
                                    "    {\"name\": \"apple\"},\n" +
                                    "    {\"name\": \"banana\"}\n" +
                                    "  ]\n" +
                                    "}")
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "삭제 완료 (응답 없음)")
    @DeleteMapping
    public void deleteItems(@RequestBody Map<String, Object> req) {
        String position = (String) req.get("position");
        assertAdmin(position);

        List<Map<String, String>> items = (List<Map<String, String>>) req.get("items");
        List<String> names = new ArrayList<>();

        for (Map<String, String> item : items) {
            names.add(item.get("name"));
        }

        itemService.deleteItems(names);
    }

    /**
     * 상품을 구매합니다.
     *
     * @param items {"apple": 5, "banana": 10}
     * @return 총 구매 금액 및 상세 항목
     */
    @Operation(
            summary = "상품 구매",
            description = "여러 상품을 동시에 구매합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "object"),
                            examples = @ExampleObject(value = "{\n" +
                                    "  \"apple\": 5,\n" +
                                    "  \"banana\": 10\n" +
                                    "}")
                    )
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "구매 결과 반환",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n" +
                            "  \"totalPrice\": 20000,\n" +
                            "  \"items\": [\n" +
                            "    {\"name\": \"apple\", \"count\": 5, \"price\": 5000},\n" +
                            "    {\"name\": \"banana\", \"count\": 10, \"price\": 15000}\n" +
                            "  ]\n" +
                            "}")
            )
    )
    @PostMapping("/purchase")
    public Map<String, Object> purchaseItems(@RequestBody Map<String, Integer> items) {
        return itemService.purchaseItems(items);
    }
}