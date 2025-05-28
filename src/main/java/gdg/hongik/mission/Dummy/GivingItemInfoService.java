package gdg.hongik.mission.Dummy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gdg.hongik.mission.Domain.ItemInfo;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class GivingItemInfoService {
    private final ItemInfo itemInfo;

    public GivingItemInfoService(ItemInfo itemInfo) {
        this.itemInfo = itemInfo;
    }

    public String givingItemInfo(String itemName) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("productionInfo.json");
        InputStream inputStream = resource.getInputStream();
        JsonNode root = objectMapper.readTree(inputStream);

        JsonNode itemNode = root.get(itemName);
        return itemNode.toString();
    }
/*
    JsonNode root = objectMapper.readTree(inputStream);
    username = root.get("name").asText();
    position = root.get("position").asText();

        System.out.println(username);
        System.out.println(position);

        userInfo.setName(username);
        userInfo.setPosition(position);

        userInformation.put("name", username);
        userInformation.put("position", position);

        return userInformation;
*/
}
