/**
 * 재고 추가 기능
 *
 * @Author : 김도현
 */

package gdg.hongik.mission.Controller.Register;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gdg.hongik.mission.Service.Register.RegisterExtraProductService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.Map;

@RestController
public class RegisterExtraProductController {
    private final RegisterExtraProductService registerExtraProductService;
    public RegisterExtraProductController(RegisterExtraProductService registerExtraProductService) {
        this.registerExtraProductService = registerExtraProductService;
    }

    @PostMapping("/api/registerExtraProduct")
    public Map<String, Object> registerExtraProduct() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("ProductPlus.json");
        InputStream inputStream = resource.getInputStream();
        ObjectNode productaddJson = (ObjectNode) objectMapper.readTree(inputStream);

        String UserPosition = productaddJson.get("position").asText();

        if (UserPosition.equals("CONSUMER")) {
            throw new RuntimeException("권한이 없습니다.");
        } else {
            return registerExtraProductService.registerExtraProduct(productaddJson);
        }
    }
}