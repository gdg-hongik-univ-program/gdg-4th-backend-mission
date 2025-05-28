/**
 * 재고구매기능의 서비스
 * CalculatingTotalController가 호출하고 실제로 output의 객체를 return하는 서비스로 연결함
 */

package gdg.hongik.mission.Service.Pockets;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CalculatingTotalService {
    private final PocketsTotalService pocketsTotalService;
    public CalculatingTotalService(PocketsTotalService pocketsTotalService) {
        this.pocketsTotalService = pocketsTotalService;
    }

    public Map<String, Object> CalculatingTotalService(JsonNode itemJsonnode) throws Exception{
        JsonNode pockets = itemJsonnode.get("items");

        List<String> itemlist = new ArrayList<>();
        List<Integer> itemTotalCount = new ArrayList<>();

        for (JsonNode item : pockets) {
            itemlist.add(item.get("name").asText());
            itemTotalCount.add(item.get("count").asInt());
        }

        return pocketsTotalService.PocketsTotalService(itemlist,itemTotalCount);
    }
}

/*

    public String givingItemInfo(String itemName) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("productionInfo.json");
        InputStream inputStream = resource.getInputStream();
        JsonNode root = objectMapper.readTree(inputStream);

        JsonNode itemNode = root.get(itemName);
        return itemNode.toString();
    }
 */
