package gdg.hongik.mission.Controller;

import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;
import gdg.hongik.mission.Model.Item;
import org.springframework.boot.autoconfigure.condition.ConditionalOnCheckpointRestore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/item")
public class ItemController {

    private final List<Item> itemList = new ArrayList<>();

    // 아이템 추가
    @PostMapping
    public String addItem(@RequestBody Item item) {
        itemList.add(item);
        return "Item added: " + item.getItemName();
    }
}