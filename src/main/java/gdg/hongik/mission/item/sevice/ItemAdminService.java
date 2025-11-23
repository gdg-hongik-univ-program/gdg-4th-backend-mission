package gdg.hongik.mission.item.sevice;

import static gdg.hongik.mission.exception.ErrorCode.ALREADY_EXIST;
import static gdg.hongik.mission.exception.ErrorCode.NOT_EXIST;

import gdg.hongik.mission.exception.CustomException;
import gdg.hongik.mission.item.domain.Item;
import gdg.hongik.mission.item.repository.ItemRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemAdminService {
    private final ItemRepository itemRepository;
    // ADMIN 인지 CHECK 필수

    @Transactional(readOnly = false)
    public void registerItem(Item item) {
        Optional<Item> foundItem = itemRepository.findItemByName(item.getName());
        if(foundItem.isPresent()) { throw new CustomException(ALREADY_EXIST);}
        itemRepository.save(item);
    }

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    @Transactional(readOnly = false)
    public Item increaseItem(String name, int quantity) {
        Optional<Item> foundItem = itemRepository.findItemByName(name);
        if(foundItem.isEmpty()) { throw new CustomException(NOT_EXIST);}
        Item item = foundItem.get();
        item.increaseQuantity(quantity);

        return item;
    }

    @Transactional(readOnly = false)
    public void deleteItems(List<String> names) {

        itemRepository.deleteByNameIn(names);
    }

    // ======== Helper ======== //
//    private void checkAdmin(User user) {
//        if(user.auth() != gdg.hongik.mission.enums.Auth.ADMIN) {
//            throw new CustomException(INVALID_AUTH);
//        }
//    }
}
