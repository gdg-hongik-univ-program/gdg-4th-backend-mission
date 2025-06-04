package gdg.hongik.mission.service;

import gdg.hongik.mission.dto.ItemDto;
import gdg.hongik.mission.dto.ItemRequestDto;
import gdg.hongik.mission.model.Item;
import gdg.hongik.mission.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 상품 관련 비즈니스 로직 처리 서비스 클래스
 * 상품 등록, 조회, 재고 추가, 삭제, 구매 기능을 포함함
 */
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * 이름으로 상품 조회
     *
     * @param name 조회할 상품 이름
     * @return 상품 DTO
     */
    public ItemDto findItem(String name) {
        Item item = itemRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
        return toDto(item);
    }

    /**
     * 새 상품 등록
     *
     * @param request 상품 등록 요청 (이름, 가격, 재고, 직책 포함)
     */
    public void registerItem(ItemRequestDto request) {
        validateAdmin(request.position());

        if (itemRepository.findByName(request.name()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 상품입니다.");
        }

        itemRepository.save(new Item(null, request.name(), request.price(), request.stock()));
    }

    /**
     * 재고 추가
     *
     * @param request 재고 추가 요청 (이름, 수량, 직책 포함)
     * @return 업데이트된 상품 DTO
     */
    public ItemDto addStock(ItemRequestDto request) {
        validateAdmin(request.position());

        Item item = itemRepository.findByName(request.name())
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

        Item updatedItem = new Item(item.getId(), item.getName(), item.getPrice(), item.getStock() + request.count());
        itemRepository.save(updatedItem);

        return toDto(updatedItem);
    }

    /**
     * 여러 상품 삭제
     *
     * @param names 삭제할 상품 이름 리스트
     * @param position 요청자 직책
     * @return 삭제 후 남은 상품 목록
     */
    public List<ItemDto> deleteItems(List<String> names, String position) {
        validateAdmin(position);

        names.stream()
                .map(itemRepository::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(itemRepository::delete);

        return itemRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    /**
     * 상품 구매를 처리합니다.
     *
     * @param requests 구매 요청 목록 (상품 이름, 수량 포함)
     * @return 총 가격 및 구매 상세 목록
     */
    public Map<String, Object> purchaseItems(List<ItemRequestDto> requests) {
        int totalPrice = 0;
        List<Map<String, Object>> resultItems = new ArrayList<>();

        for (ItemRequestDto req : requests) {
            Item item = itemRepository.findByName(req.name())
                    .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));

            if (item.getStock() < req.count()) {
                throw new IllegalArgumentException("재고가 부족합니다: " + req.name());
            }

            Item updatedItem = new Item(item.getId(), item.getName(), item.getPrice(), item.getStock() - req.count());
            itemRepository.save(updatedItem);

            int price = req.count() * item.getPrice();
            totalPrice += price;

            resultItems.add(Map.of(
                    "name", req.name(),
                    "count", req.count(),
                    "price", price
            ));
        }

        return Map.of(
                "totalPrice", totalPrice,
                "items", resultItems
        );
    }

    /**
     * 관리자 권한을 확인합니다.
     *
     * @param position 요청자의 직책
     */
    private void validateAdmin(String position) {
        if (!"ADMIN".equalsIgnoreCase(position)) {
            throw new IllegalArgumentException("관리자 권한이 필요합니다.");
        }
    }

    /**
     * Item 엔티티를 DTO로 변환합니다.
     *
     * @param item Item 엔티티
     * @return ItemDto
     */
    private ItemDto toDto(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStock());
    }
}
