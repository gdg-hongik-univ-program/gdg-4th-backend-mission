package gdg.hongik.mission.Service.Pockets;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReturningTotalService {
    private final PocketsTotalService pocketsTotalService;
    public ReturningTotalService(PocketsTotalService pocketsTotalService) {
        this.pocketsTotalService = pocketsTotalService;
    }

    public JSONObject ReturningTotalService(JsonNode itemJsonnode) throws Exception{
        List<String> itemlist = new ArrayList<>();
        List<Integer> itemTotalCount = new ArrayList<>();

        JsonNode itemsNode = itemJsonnode.get("items");

        for (JsonNode item : itemsNode) {
            itemlist.add(item.get("name").asText());
            itemTotalCount.add(item.get("count").asInt());
        }

        return pocketsTotalService.PocketsTotalService(itemlist,itemTotalCount);
    }
}
