package gdg.hongik.mission.Service.UserSend;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

@Service
public class DataSendingService {
    private final ItemJSONService itemJSONService;
    public DataSendingService(ItemJSONService itemJSONService) {
        this.itemJSONService = itemJSONService;
    }

    public Map<String, Object> CustomerSendInfo(String itemName) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("productionInfo.json");
        InputStream inputStream = resource.getInputStream();
        JsonNode root = objectMapper.readTree(inputStream);

        JsonNode itemNode = root.get(itemName);
        Map<String,Object> itemInformations = objectMapper.convertValue(itemNode, Map.class);

        return itemInformations;
    }
}
/*
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("productionInfo.json");
        InputStream inputStream = resource.getInputStream();
        JsonNode root = objectMapper.readTree(inputStream);

        JsonNode itemNode = root.get(itemName);
        return itemNode.toString();
 */