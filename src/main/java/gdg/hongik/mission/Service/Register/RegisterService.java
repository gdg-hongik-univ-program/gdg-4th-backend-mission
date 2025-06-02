package gdg.hongik.mission.Service.Register;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegisterService {
    public void register(JsonNode registerProductionJson) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("productionInfo.json");
        InputStream inputStream = resource.getInputStream();
        JsonNode itemJsonnode = objectMapper.readTree(inputStream);

        ClassPathResource resource2 = new ClassPathResource("RegisterProductionList.json");
        InputStream inputStream2 = resource2.getInputStream();
        ObjectNode productionListJson = (ObjectNode) objectMapper.readTree(inputStream2);

        String itemName = registerProductionJson.get("name").asText();

        List<String> itemlist = new ArrayList<>();
        List<Integer> itemTotalCount = new ArrayList<>();

        for (JsonNode item : itemJsonnode) {
            if (item.get("name").asText().equals(itemName)) {
                throw new RuntimeException("이미 등록된 상품입니다.");
            }
        }

        productionListJson.put(itemName, registerProductionJson);
    }
}
