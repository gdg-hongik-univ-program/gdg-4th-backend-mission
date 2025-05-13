
package gdg.hongik.mission.domain.item.controller;

import gdg.hongik.mission.domain.item.dto.request.ItemAddStockRequest;
import gdg.hongik.mission.domain.item.dto.request.ItemCreateRequest;
import gdg.hongik.mission.domain.item.dto.request.ItemSearchRequest;
import gdg.hongik.mission.domain.item.dto.response.ItemResponse;
import gdg.hongik.mission.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemCreateRequest request) {
        ItemResponse response = itemService.createItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getAllItems() {
        List<ItemResponse> responses = itemService.getAllItems();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemResponse> getItemById(@PathVariable Long id) {
        ItemResponse response = itemService.getItemById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ItemResponse> searchItem(@ModelAttribute ItemSearchRequest request) {
        ItemResponse response = itemService.getItemByName(request.getName());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/stock")
    public ResponseEntity<ItemResponse> addStock(@RequestBody ItemAddStockRequest request) {
        ItemResponse response = itemService.addStock(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
} 