/**
 * 재고 삭제 기능
 *
 * @position : 권한, UserInfoSettingService에서 프로그램이 시작될 때 미리 저장됨
 *             추후에 post 요청이 왔을 때 읽어서 보내도록 수정할 예정, 수정예상시각 : 3초
 */

package gdg.hongik.mission.Controller.Register;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import gdg.hongik.mission.Domain.UserInfo;
import gdg.hongik.mission.Service.Register.DeleteProductService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.Map;

@RestController
public class DeleteProductController {
    private final DeleteProductService deleteProductService;
    private final UserInfo userInfo;
    public DeleteProductController(DeleteProductService deleteProductService, UserInfo userInfo) {
        this.deleteProductService = deleteProductService;
        this.userInfo = userInfo;
    }

    @PostMapping("/api/delete")
    public Map<String, Object> RegisterProduction() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("DeleteProductionList.json");
        InputStream inputStream = resource.getInputStream();
        ObjectNode DelteProductNode = (ObjectNode) objectMapper.readTree(inputStream);
        String UserPosition = DelteProductNode.get("position").asText();


        if (UserPosition.equals("CONSUMER")) {
            throw new RuntimeException("권한이 없습니다.");
        } else {
            return deleteProductService.deleteProduct(DelteProductNode);
        }
    }
}
