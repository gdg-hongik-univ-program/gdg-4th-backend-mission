package gdg.hongik.mission.Service.UserSend;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdg.hongik.mission.Repository.ItemRepository;
import gdg.hongik.mission.Repository.UserRepository;
import org.json.simple.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class ItemJSONService  {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemJSONService (ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    public Map<String, Object> getItemJSON(String itemName) throws Exception {
        JSONObject userJSON = userRepository.getUserJSON();

        if (!checkingInRepo(itemName)) {
            throw new RuntimeException("No item in stock");
        }

        userJSON.put("item", itemName);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.readValue(userJSON.toJSONString(), Map.class);
        return map;
    }
    /*
    public Map<String,Object> getItemJSON() throws Exception {
        String itemName = itemRepository.inputItem();

        if (checkingInRepo(itemName)) {
            JSONObject userJSON = userRepository.getUserJSON();

            JSONObject sendingJSON = new JSONObject();

            sendingJSON = userJSON;
            sendingJSON.put("item", itemName);

            return sendingJSON;
        }
        else {
            throw new RuntimeException("No item in stock");
        }
    }
*/
    public boolean checkingInRepo(String itemName) throws Exception {
        ClassPathResource resource = new ClassPathResource("productionInfo.json");
        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(itemName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
