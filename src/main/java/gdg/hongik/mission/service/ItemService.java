package gdg.hongik.mission.service;

import gdg.hongik.mission.domain.Item;
import gdg.hongik.mission.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // 스프링 빈 등록
public class ItemService {

    private final ItemRepository itemRepository;

    // 생성자 주입
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * 상품 등록
     * 이미 이름이 존재하면 예외 발생
     */
    public void addItem(String name, int price, int stock) {
        if (itemRepository.existsByName(name)) {
            throw new IllegalArgumentException("이미 존재하는 상품입니다.");
        }
        Item item = new Item(name, price, stock);
        itemRepository.save(item);
    }

    /**
     * 상품 전체 조회
     */
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    /**
     * 이름으로 상품 조회
     */
    public Item getItemByName(String name) {
        return itemRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
    }

    /**
     * 이름으로 상품 재고 추가
     */
    public int addStock(String name, int count) {
        Item item = getItemByName(name);
        item.setStock(item.getStock() + count);
        itemRepository.save(item); // JPA의 변경 감지에 의해 save 생략도 가능
        return item.getStock();
    }

    /**
     * 여러 상품 삭제 (이름 기준)
     */
    public void deleteItems(List<String> names) {
        for (String name : names) {
            itemRepository.deleteByName(name);
        }
    }

    /**
     * 상품 구매 (여러 상품 한 번에)
     */
    public int purchaseItems(List<PurchaseItem> items) {
        int totalPrice = 0;
        for (PurchaseItem purchase : items) {
            Item item = getItemByName(purchase.getName());
            if (item.getStock() < purchase.getCount()) {
                throw new IllegalArgumentException("재고 부족: " + item.getName());
            }
            item.setStock(item.getStock() - purchase.getCount());
            totalPrice += item.getPrice() * purchase.getCount();
            itemRepository.save(item); // 변경 감지에 의해 DB 반영
        }
        return totalPrice;
    }

    // (구매용 내부 DTO, 나중에 외부 DTO로 대체해도 됩니다)
    public static class PurchaseItem {
        private String name;
        private int count;

        // 생성자/Getter/Setter
        public PurchaseItem(String name, int count) {
            this.name = name;
            this.count = count;
        }
        public String getName() { return name; }
        public int getCount() { return count; }
        public void setName(String name) { this.name = name; }
        public void setCount(int count) { this.count = count; }
    }
}