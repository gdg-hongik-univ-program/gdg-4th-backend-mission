/**
 * 새 재고 등록 기능
 *
 * @Author : 김도현
 */
package gdg.hongik.mission.Controller.Register;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gdg.hongik.mission.Domain.UserInfo;
import gdg.hongik.mission.Service.Register.RegisterService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController
public class RegisterProductController {
    private RegisterService registerService;
    private final UserInfo userInfo;
    public RegisterProductController(RegisterService registerService, UserInfo userInfo) {
        this.registerService = registerService;
        this.userInfo = userInfo;
    }

    @PostMapping("/api/register")
    public void RegisterProduction() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("RegisterProductionList.json");
        InputStream inputStream = resource.getInputStream();
        ObjectNode RegisterproductionNode = (ObjectNode) objectMapper.readTree(inputStream);
        String UserPosition = RegisterproductionNode.get("position").asText();

        if (UserPosition.equals("CONSUMER")) {
            throw new RuntimeException("권한이 없습니다.");
        } else {
            registerService.register(RegisterproductionNode);
        }
    }
}
