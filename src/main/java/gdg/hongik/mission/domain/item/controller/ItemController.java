package gdg.hongik.mission.domain.item.controller;

import gdg.hongik.mission.domain.item.dto.request.ItemAddStockRequest;
import gdg.hongik.mission.domain.item.dto.request.ItemCreateRequest;
import gdg.hongik.mission.domain.item.dto.request.ItemSearchRequest;
import gdg.hongik.mission.domain.item.dto.response.ItemResponse;
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
public class ItemController {

    private final ItemService itemService;

    /**
     * 새로운 상품을 생성합니다
     * 
     * @param request 상품 생성 요청 정보
     * @return 생성된 상품 정보와 201 상태 코드
     */
    @Operation(summary = "상품 생성", description = "새로운 상품을 등록합니다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "상품 생성 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청 (이미 존재하는 상품명)")
    })
    @PostMapping
    public ResponseEntity<ItemResponse> createItem(
            @Parameter(description = "상품 생성 요청 정보", required = true)
            @RequestBody ItemCreateRequest request) {
        ItemResponse response = itemService.createItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 모든 상품 목록을 조회합니다
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
     * ID로 특정 상품을 조회합니다
     * 
     * @param id 조회할 상품 ID
     * @return 조회된 상품 정보와 200 상태 코드
     */
    @Operation(summary = "상품 ID로 조회", description = "상품 ID를 사용하여 특정 상품을 조회합니다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "상품 조회 성공"),
        @ApiResponse(responseCode = "400", description = "존재하지 않는 상품")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(
            @Parameter(description = "조회할 상품 ID", required = true)
            @PathVariable Long id) {
        ItemResponse response = itemService.getItemById(id);
        return ResponseEntity.ok(response);
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
    @GetMapping("/search")
    public ResponseEntity<ItemResponse> searchItem(
            @Parameter(description = "상품 검색 요청 정보", required = true)
            @ModelAttribute ItemSearchRequest request) {
        ItemResponse response = itemService.getItemByName(request.getName());
        return ResponseEntity.ok(response);
    }

    /**
     * 상품 재고를 추가합니다
     * 
     * @param request 재고 추가 요청 정보
     * @return 업데이트된 상품 정보와 200 상태 코드
     */
    @Operation(summary = "상품 재고 추가", description = "기존 상품의 재고를 추가합니다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "재고 추가 성공"),
        @ApiResponse(responseCode = "400", description = "존재하지 않는 상품")
    })
    @PatchMapping("/stock")
    public ResponseEntity<ItemResponse> addStock(
            @Parameter(description = "재고 추가 요청 정보", required = true)
            @RequestBody ItemAddStockRequest request) {
        ItemResponse response = itemService.addStock(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 상품을 삭제합니다
     * 
     * @param id 삭제할 상품 ID
     * @return 204 상태 코드
     */
    @Operation(summary = "상품 삭제", description = "상품 ID를 사용하여 상품을 삭제합니다")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "상품 삭제 성공"),
        @ApiResponse(responseCode = "400", description = "존재하지 않는 상품")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(
            @Parameter(description = "삭제할 상품 ID", required = true)
            @PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
} 