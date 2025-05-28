package gdg.hongik.mission.item.sevice;

import static gdg.hongik.mission.exception.ErrorCode.INVALID_INPUT;
import static gdg.hongik.mission.exception.ErrorCode.NOT_EXIST;

import gdg.hongik.mission.cart.domain.Cart;
import gdg.hongik.mission.exception.CustomException;
import gdg.hongik.mission.item.domain.Item;
import gdg.hongik.mission.item.repository.ItemRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemUserService {
    private final ItemRepository itemRepository;

    @Transactional(readOnly = false)
    public List<Item> buyItems(Cart cart) {
        List<String> names = cart.getItemNames();
        Map<String, Integer> order = IntStream.range(0, names.size())
                .boxed()
                .collect(Collectors.toMap(names::get, Function.identity()));

        List<Item> stocks = itemRepository.findItemsByNames(names);

        Logger logger = org.slf4j.LoggerFactory.getLogger(ItemUserService.class);
        logger.info(">>> fetched stocks count={} : {}", stocks.size(),
                stocks.stream().map(Item::getName).toList());
        logger.info("=============== buyItems cart: {}", cart.getItemOrders().keySet());
        logger.info("=============== buyItems: name: {}", names);
        logger.info("=============== buyItems order: {}", order);

        return stocks.stream().map(item -> {
            int quantity = cart.getItemOrder(item.getName()).getQuantity();

            logger.info("=============== buyItems stream: {}, {}", item.getName(), quantity);

            if(quantity > item.getQuantity()) {
                throw new CustomException(INVALID_INPUT, "quantity must be less than or equal to the stock quantity");
            }

            item.decreaseQuantity(quantity);
            return new Item(item.getName(), quantity, item.getPrice());
        })
        .sorted(Comparator.comparingInt(item -> order.getOrDefault(item.getName(), Integer.MAX_VALUE))).toList();
    }

    public List<Item> findAllItems() {
        return itemRepository.findAll();
    }

    public Item findItemByName(String name) {
        Optional<Item> item = itemRepository.findItemByName(name);
        if(item.isPresent()) {
            return item.get();
        } else {
            throw new CustomException(NOT_EXIST);
        }
    }
}
