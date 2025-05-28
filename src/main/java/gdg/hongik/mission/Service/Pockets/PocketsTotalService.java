/**
 * 재고구매 기능에서 실제 output 할 객체를 만드는 서비스
 */
package gdg.hongik.mission.Service.Pockets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class PocketsTotalService {

    public JSONObject PocketsTotalService(List itemlist, List itemTotalCount) throws Exception {
        int totalPrice = 0;
        List<JSONObject> rst = new ArrayList<>();
        JSONObject resultObject = new JSONObject();

        for (int i = 0; i < itemlist.size(); i++) {
            JSONObject pocketsTotal = new JSONObject();
            String itemName = (String) itemlist.get(i); //이름
            int itemTotalCountValue = (int) itemTotalCount.get(i); //수량

            ObjectMapper objectMapper = new ObjectMapper();
            ClassPathResource resource = new ClassPathResource("productionInfo.json");
            InputStream inputStream = resource.getInputStream();
            JsonNode root = objectMapper.readTree(inputStream);
            JsonNode itemNode = root.get(itemName);

            pocketsTotal.put("name", itemName);
            pocketsTotal.put("count", itemTotalCountValue);
            pocketsTotal.put("price", itemNode.get("price").asInt() * itemTotalCountValue);

            int totalCount = itemTotalCountValue * itemNode.get("price").asInt(); //추가될 금액
            totalPrice += totalCount; //최종금액
            rst.add(pocketsTotal);
        }

        resultObject.put("totalPrice", totalPrice);
        resultObject.put("items", rst);

        return resultObject;
    }
}
