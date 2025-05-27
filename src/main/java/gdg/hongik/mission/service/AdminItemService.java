package gdg.hongik.mission.service;

import gdg.hongik.mission.DTO.RegisterItemRequest;
import gdg.hongik.mission.entity.Item;
import gdg.hongik.mission.entity.User;
import gdg.hongik.mission.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminItemService {
    private final ItemRepository itemRepository;

    public AdminItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * 새 상품을 등록합니다. 중복되면 예외를 발생시킵니다.
     * @param req 상품 등록 요청 DTO
     */
    public void register(RegisterItemRequest req) {
        if (req.getPosition() != User.Role.ADMIN)
            throw new IllegalArgumentException("관리자만 등록할 수 있습니다.");
        if (itemRepository.findByName(req.getName()).isPresent())
            throw new IllegalArgumentException("이미 존재하는 상품입니다.");

        itemRepository.save(Item.builder()
                .name(req.getName())
                .price(req.getPrice())
                .stock(req.getStock())
                .build());
    }

    /**
     * 특정 상품의 재고를 증가시킵니다.
     * @param name 상품 이름
     * @param count 추가할 수량
     * @param role 요청자의 역할
     * @return 수정된 상품 정보
     */
    public Item addStock(String name, int count, User.Role role) {
        if (role != User.Role.ADMIN)
            throw new IllegalArgumentException("관리자만 재고를 추가할 수 있습니다.");
        Item item = itemRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("상품 없음"));
        item.setStock(item.getStock() + count);
        return itemRepository.save(item);
    }

    /**
     * 특정 이름 목록에 해당하는 상품들을 삭제합니다.
     * @param names 삭제할 상품 이름 목록
     * @param role 요청자의 역할
     * @return 삭제 후 남아있는 모든 상품 목록
     */
    public List<Map<String, Object>> deleteItems(List<String> names, User.Role role) {
        if (role != User.Role.ADMIN)
            throw new IllegalArgumentException("관리자만 삭제할 수 있습니다.");

        for (String name : names) {
            itemRepository.findByName(name).ifPresent(itemRepository::delete);
        }

        return itemRepository.findAll().stream().map(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", item.getName());
            map.put("stock", item.getStock());
            return map;
        }).toList();
    }
}
