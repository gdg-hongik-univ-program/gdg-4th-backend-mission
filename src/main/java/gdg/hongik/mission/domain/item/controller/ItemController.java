package gdg.hongik.mission.domain.item.controller;

import gdg.hongik.mission.domain.item.dto.request.ItemAddStockRequest;
import gdg.hongik.mission.domain.item.dto.request.ItemCreateRequest;
import gdg.hongik.mission.domain.item.dto.request.ItemDeleteRequest;
import gdg.hongik.mission.domain.item.dto.request.ItemPurchaseRequest;
import gdg.hongik.mission.domain.item.dto.request.ItemSearchRequest;
import gdg.hongik.mission.domain.item.dto.response.ItemResponse;
import gdg.hongik.mission.domain.item.dto.response.PurchaseResponse;
import gdg.hongik.mission.domain.item.dto.response.StockResponse;
import gdg.hongik.mission.domain.item.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 상품 관련 REST API를 제공하는 컨트롤러 클래스
 */
@Tag(name = "상품 관리", description = "상품 CRUD API")
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ItemController {

    private final ItemService itemService;

    /**
     * 전체 상품 목록을 조회합니다
     * 
     * @return 상품 목록과 200 상태 코드
     */
    @Operation(summary = "전체 상품 조회", description = "등록된 모든 상품 목록을 조회합니다")
    @ApiResponse(responseCode = "200", description = "상품 목록 조회 성공")
    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        List<ItemResponse> responses = itemService.getAllItems();
        return ResponseEntity.ok(responses);
    }

    /**
     * 상품명으로 상품을 검색합니다
     * 
     * @param request 상품 검색 요청 정보
     * @return 검색된 상품 정보와 200 상태 코드
     */
    @Operation(summary = "상품명으로 검색", description = "상품명을 사용하여 상품을 검색합니다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "상품 검색 성공"),
        @ApiResponse(responseCode = "400", description = "존재하지 않는 상품")
    })
    @PostMapping("/search")
    public ResponseEntity<ItemResponse> searchItem(
            @Parameter(description = "상품 검색 요청 정보", required = true)
            @RequestBody ItemSearchRequest request) {
        ItemResponse response = itemService.getItemByName(request.getItemName());
        return ResponseEntity.ok(response);
    }

    /**
     * 상품을 구매합니다
     * 
     * @param request 구매 요청 정보
     * @return 구매 결과 정보와 200 상태 코드
     */
    @Operation(summary = "상품 구매", description = "장바구니의 상품들을 구매합니다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "구매 성공"),
        @ApiResponse(responseCode = "400", description = "존재하지 않는 상품이거나 재고 부족")
    })
    @PostMapping("/buy")
    public ResponseEntity<PurchaseResponse> purchaseItems(
            @Parameter(description = "구매 요청 정보", required = true)
            @RequestBody ItemPurchaseRequest request) {
        PurchaseResponse response = itemService.purchaseItems(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 새로운 상품을 등록합니다
     * 
     * @param request 상품 생성 요청 정보
     * @return 생성된 상품 정보와 201 상태 코드
     */
    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "상품 등록 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 (이미 존재하는 상품명)")
    })
    @PostMapping("/register")
    public ResponseEntity<ItemResponse> registerItem(
            @Parameter(description = "상품 등록 요청 정보", required = true)
            @RequestBody ItemCreateRequest request) {
        ItemResponse response = itemService.createItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 상품 재고를 추가합니다
     * 
     * @param request 재고 추가 요청 정보
     * @return 업데이트된 재고 정보와 200 상태 코드
     */
    @Operation(summary = "상품 재고 추가", description = "기존 상품의 재고를 추가합니다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "재고 추가 성공"),
        @ApiResponse(responseCode = "400", description = "존재하지 않는 상품")
    })
    @PostMapping("/increase")
    public ResponseEntity<StockResponse> increaseStock(
            @Parameter(description = "재고 추가 요청 정보", required = true)
            @RequestBody ItemAddStockRequest request) {
        StockResponse response = itemService.addStock(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 상품들을 삭제합니다
     * 
     * @param request 삭제할 상품들 정보
     * @return 204 상태 코드
     */
    @Operation(summary = "상품 삭제", description = "상품명을 사용하여 상품들을 삭제합니다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "상품 삭제 성공"),
        @ApiResponse(responseCode = "400", description = "존재하지 않는 상품")
    })
    @PostMapping("/delete")
    public ResponseEntity<Void> deleteItems(
            @Parameter(description = "삭제할 상품들 정보", required = true)
            @RequestBody ItemDeleteRequest request) {
        itemService.deleteItems(request);
        return ResponseEntity.noContent().build();
    }
} 