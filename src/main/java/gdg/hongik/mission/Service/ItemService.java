import gdg.hongik.mission.Model.Item;
import gdg.hongik.mission.Repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
        private final ItemRepository itemRepository;
        public void saveItem(Item item){ itemRepository.save(item);}
        public Item itemView(String itemName) { return itemRepository.findByName(itemName).get();}

    public Item findByName(String name) {
            return itemRepository.findByName(name)
                    .orElseThrow(() -> new IllegalArgumentException("해당 이름의 상품이 없습니다."));
    }
}
