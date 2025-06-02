package gdg.hongik.mission.Service.Register;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.json.simple.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DeleteProductService {
    private final DeletService deletService;
    public DeleteProductService(DeletService deletService) {
        this.deletService = deletService;
    }

    public Map<String, Object> deleteProduct(JsonNode deletesJson) throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode deleteNode = deletesJson.get("items");
        List<String> deleteProductionsName = new ArrayList<>();

        for (JsonNode deleteItem : deleteNode) {
            deleteProductionsName.add(deleteItem.get("name").asText());
        }

        return deletService.DeleteService(deleteProductionsName);

    }
}
