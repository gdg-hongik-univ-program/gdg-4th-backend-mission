package gdg.hongik.mission.Service.Register;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

@Service
public class RegisterExtraProductService {
    private enactExtraProductService enactExtraProductService;
    public RegisterExtraProductService(enactExtraProductService enactExtraProductService) {
        this.enactExtraProductService = enactExtraProductService;
    }

    public Map<String, Object> registerExtraProduct(ObjectNode registerProductList) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode productaddJson = (ObjectNode) registerProductList;
        Map<String, Object> productadd = objectMapper.convertValue(productaddJson, Map.class);

        return enactExtraProductService.enactExtraProduct(productadd);
    }
}
