package gdg.hongik.mission.domain.item.service;

import gdg.hongik.mission.domain.item.dto.request.ItemAddStockRequest;
import gdg.hongik.mission.domain.item.dto.request.ItemCreateRequest;
import gdg.hongik.mission.domain.item.dto.request.ItemDeleteRequest;
import gdg.hongik.mission.domain.item.dto.request.ItemPurchaseRequest;
import gdg.hongik.mission.domain.item.dto.response.ItemResponse;
import gdg.hongik.mission.domain.item.dto.response.PurchaseResponse;
import gdg.hongik.mission.domain.item.dto.response.StockResponse;
import gdg.hongik.mission.domain.item.entity.Item;
import gdg.hongik.mission.domain.item.repository.ItemRepository;
import gdg.hongik.mission.domain.purchase.entity.PurchaseHistory;
import gdg.hongik.mission.domain.purchase.entity.PurchaseHistoryItem;
import gdg.hongik.mission.domain.purchase.repository.PurchaseHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 상품 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;

    /**
     * 새로운 상품을 생성합니다
     * 
     * @param request 상품 생성 요청 정보
     * @return 생성된 상품 정보
     * @throws IllegalArgumentException 이미 존재하는 상품명인 경우
     */
    @Transactional
    public ItemResponse createItem(ItemCreateRequest request) {
        if (itemRepository.existsByName(request.getItemName())) {
            throw new IllegalArgumentException("이미 존재하는 상품명입니다.");
        }
        
        Item item = request.toEntity();
        Item savedItem = itemRepository.save(item);
        
        return ItemResponse.of(savedItem);
    }

    /**
     * 모든 상품 목록을 조회합니다
     * 
     * @return 상품 목록
     */
    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll().stream()
                .map(ItemResponse::of)
                .collect(Collectors.toList());
    }

    /**
     * ID로 특정 상품을 조회합니다
     * 
     * @param id 조회할 상품 ID
     * @return 조회된 상품 정보
     * @throws IllegalArgumentException 존재하지 않는 상품인 경우
     */
    public ItemResponse getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        
        return ItemResponse.of(item);
    }

    /**
     * 상품명으로 특정 상품을 조회합니다
     * 
     * @param itemName 조회할 상품명
     * @return 조회된 상품 정보
     * @throws IllegalArgumentException 존재하지 않는 상품인 경우
     */
    public ItemResponse getItemByName(String itemName) {
        Item item = itemRepository.findByName(itemName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        
        return ItemResponse.of(item);
    }

    /**
     * 상품 재고를 추가합니다
     * 
     * @param request 재고 추가 요청 정보
     * @return 업데이트된 재고 정보
     * @throws IllegalArgumentException 존재하지 않는 상품인 경우
     */
    @Transactional
    public StockResponse addStock(ItemAddStockRequest request) {
        Item item = itemRepository.findByName(request.getItemName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        
        item.addStock(request.getCount());
        
        return new StockResponse(item.getName(), item.getStock());
    }

    /**
     * 상품을 구매합니다
     * 
     * @param request 구매 요청 정보
     * @return 구매 결과 정보
     * @throws IllegalArgumentException 존재하지 않는 상품이거나 재고가 부족한 경우
     */
    @Transactional
    public PurchaseResponse purchaseItems(ItemPurchaseRequest request) {
        List<PurchaseResponse.PurchasedItem> purchasedItems = request.getItems().stream()
                .map(purchaseItem -> {
                    Item item = itemRepository.findByName(purchaseItem.getItemName())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다: " + purchaseItem.getItemName()));
                    
                    if (item.getStock() < purchaseItem.getCount()) {
                        throw new IllegalArgumentException("재고가 부족합니다. 상품: " + item.getName() + ", 요청 수량: " + purchaseItem.getCount() + ", 현재 재고: " + item.getStock());
                    }
                    
                    // 재고 차감
                    item.reduceStock(purchaseItem.getCount());
                    
                    return new PurchaseResponse.PurchasedItem(
                            item.getName(),
                            item.getPrice(),
                            purchaseItem.getCount()
                    );
                })
                .collect(Collectors.toList());
        
        int totalPrice = purchasedItems.stream()
                .mapToInt(item -> item.getPrice() * item.getCount())
                .sum();

        // 구매 기록 저장
        savePurchaseHistory(request.getUserName(), totalPrice, purchasedItems);
        
        return new PurchaseResponse(totalPrice, purchasedItems);
    }

    /**
     * 구매 기록을 저장합니다
     * 
     * @param userName 구매자명
     * @param totalPrice 총 결제 금액
     * @param purchasedItems 구매한 상품 목록
     */
    private void savePurchaseHistory(String userName, int totalPrice, List<PurchaseResponse.PurchasedItem> purchasedItems) {
        PurchaseHistory purchaseHistory = PurchaseHistory.builder()
                .userName(userName)
                .totalPrice(totalPrice)
                .build();

        for (PurchaseResponse.PurchasedItem purchasedItem : purchasedItems) {
            PurchaseHistoryItem historyItem = PurchaseHistoryItem.builder()
                    .itemName(purchasedItem.getItemName())
                    .count(purchasedItem.getCount())
                    .price(purchasedItem.getPrice())
                    .build();

            purchaseHistory.addPurchaseItem(historyItem);
        }

        purchaseHistoryRepository.save(purchaseHistory);
    }

    /**
     * 상품들을 삭제합니다
     * 
     * @param request 삭제 요청 정보
     * @throws IllegalArgumentException 존재하지 않는 상품인 경우
     */
    @Transactional
    public void deleteItems(ItemDeleteRequest request) {
        for (ItemDeleteRequest.DeleteItem deleteItem : request.getItems()) {
            Item item = itemRepository.findByName(deleteItem.getItemName())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다: " + deleteItem.getItemName()));
            
            itemRepository.delete(item);
        }
    }

    /**
     * 상품을 삭제합니다
     * 
     * @param id 삭제할 상품 ID
     * @throws IllegalArgumentException 존재하지 않는 상품인 경우
     */
    @Transactional
    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
        
        itemRepository.deleteById(id);
    }
} 