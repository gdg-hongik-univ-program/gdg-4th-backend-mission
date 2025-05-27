package gdg.hongik.mission.domain.item.service;

import gdg.hongik.mission.domain.item.dto.request.ItemAddStockRequest;
import gdg.hongik.mission.domain.item.dto.request.ItemCreateRequest;
import gdg.hongik.mission.domain.item.dto.response.ItemResponse;
import gdg.hongik.mission.domain.item.entity.Item;
import gdg.hongik.mission.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 상품 관련 비즈니스 로직을 처리하는 서비스 클래스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    /**
     * 새로운 상품을 생성합니다
     * 
     * @param request 상품 생성 요청 정보
     * @return 생성된 상품 정보
     * @throws IllegalArgumentException 이미 존재하는 상품명인 경우
     */
    @Transactional
    public ItemResponse createItem(ItemCreateRequest request) {
        if (itemRepository.existsByName(request.getName())) {
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
     * @param name 조회할 상품명
     * @return 조회된 상품 정보
     * @throws IllegalArgumentException 존재하지 않는 상품인 경우
     */
    public ItemResponse getItemByName(String name) {
        Item item = itemRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        
        return ItemResponse.of(item);
    }

    /**
     * 상품 재고를 추가합니다
     * 
     * @param request 재고 추가 요청 정보
     * @return 업데이트된 상품 정보
     * @throws IllegalArgumentException 존재하지 않는 상품인 경우
     */
    @Transactional
    public ItemResponse addStock(ItemAddStockRequest request) {
        Item item = itemRepository.findByName(request.getName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        
        item.addStock(request.getCount());
        
        return ItemResponse.of(item);
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