package gdg.hongik.mission.item;

import gdg.hongik.mission.user.UserRepository;
import gdg.hongik.mission.user.UserService;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 상품 관리 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserService userService;

    /**
     * ItemService 생성자.
     *
     * @param itemRepository ItemRepository 주입
     * @param userService UserService 주입
     */
    public ItemService(ItemRepository itemRepository, UserService userService) {
        this.itemRepository = itemRepository;
        this.userService = userService;
    }

    /**
     * 새로운 상품을 등록합니다. (관리자 전용)
     *
     * @param name 상품 이름
     * @param price 상품 가격
     * @param stock 상품 재고
     * @return 등록된 상품 객체
     * @throws IllegalArgumentException 이미 존재하는 상품일 경우
     */
    public Item registerItem(String name, int price, int stock) {
        if (itemRepository.existsByName(name)) {
            throw new IllegalArgumentException("이미 존재하는 상품입니다.");
        }
        return itemRepository.save(new Item(name, price, stock));
    }

    /**
     * 상품 이름으로 상품 검색.
     *
     * @param name 상품 이름
     * @return 상품 객체
     * @throws IllegalArgumentException 상품을 찾을 수 없는 경우
     */
    public Item findByName(String name) {
        return itemRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));
    }

    /**
     * 모든 상품 조회.
     *
     * @return 상품 목록
     */
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    /**
     * 상품 재고 추가.
     *
     * @param name 상품 이름
     * @param count 추가할 재고 수량
     */
    public void addStock(String name, int count) {
        Item item = findByName(name);
        item.increaseStock(count);
        itemRepository.save(item);
    }

    /**
     * 상품 삭제.
     *
     * @param name 삭제할 상품 이름
     */
    public void deleteItem(String name) {
        itemRepository.deleteByName(name);
    }
}
