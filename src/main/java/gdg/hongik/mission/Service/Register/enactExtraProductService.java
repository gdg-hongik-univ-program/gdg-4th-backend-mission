package gdg.hongik.mission.Service.Register;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class enactExtraProductService {
    public Map<String,Object> enactExtraProduct(Map<String, Object> productadd) throws Exception{
        String productName = (String) productadd.get("name");
        int count = (int) productadd.get("count");
        JSONObject result = new JSONObject();

        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("productionInfo.json");
        Map<String, Object> productionInfo = objectMapper.readValue(resource.getInputStream(), Map.class);
        Map<String, Object> productInfo = (Map<String, Object>) productionInfo.get(productName);
        int stock = (int) productInfo.get("stock");
        productInfo.put("stock", stock + count);

        result.put("name", productName);
        result.put("stock", stock + count);

        Map<String, Object> res = (Map<String, Object>) result;
        return res;
    }
}

/*
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("ProductPlus.json");
        InputStream inputStream = resource.getInputStream();
        ObjectNode productaddJson = (ObjectNode) objectMapper.readTree(inputStream);
        Map<String, Object> productadd = objectMapper.convertValue(productaddJson, Map.class);
 */