package gdg.hongik.mission.Service.Register;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeletService {
    public Map<String ,Object> DeleteService(List<String> deletesJson) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("productionInfo.json");
        InputStream inputStream = resource.getInputStream();
        ObjectNode productionList = (ObjectNode) objectMapper.readTree(inputStream);

        for (String deleteJson : deletesJson) {
            productionList.remove(deleteJson);
        }

        Map<String ,Object> productionListMap = objectMapper.convertValue(productionList, Map.class);
        Map<String, Object> res = new HashMap<>();

        res.put("items", productionListMap);
        return res;
    }
}
