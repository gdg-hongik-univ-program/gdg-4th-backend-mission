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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public ItemResponse createItem(ItemCreateRequest request) {
        if (itemRepository.existsByName(request.getName())) {
            throw new IllegalArgumentException("이미 존재하는 상품명입니다.");
        }
        
        Item item = request.toEntity();
        Item savedItem = itemRepository.save(item);
        
        return ItemResponse.of(savedItem);
    }

    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll().stream()
                .map(ItemResponse::of)
                .collect(Collectors.toList());
    }

    public ItemResponse getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        
        return ItemResponse.of(item);
    }

    public ItemResponse getItemByName(String name) {
        Item item = itemRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        
        return ItemResponse.of(item);
    }

    @Transactional
    public ItemResponse addStock(ItemAddStockRequest request) {
        Item item = itemRepository.findByName(request.getName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
        
        item.addStock(request.getCount());
        
        return ItemResponse.of(item);
    }

    @Transactional
    public void deleteItem(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }
        
        itemRepository.deleteById(id);
    }
} 