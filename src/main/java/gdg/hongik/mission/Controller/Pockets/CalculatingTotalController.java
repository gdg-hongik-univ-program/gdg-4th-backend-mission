/**
 * 재고구매 기능
 *
 * @Param itemJsonnode : 장바구니 목록, post 요청을 받는다면 로컬에서 긁어오는게 아니라 데이터를 전송받을 예정. 프론트엔드와 합칠 때 수정
 * @Author 김도현
 */

package gdg.hongik.mission.Controller.Pockets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gdg.hongik.mission.Service.Pockets.CalculatingTotalService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.Map;

@RestController
public class CalculatingTotalController {
    private final CalculatingTotalService CalculatingTotalService;

    public CalculatingTotalController(CalculatingTotalService CalculatingTotalService) {
        this.CalculatingTotalService = CalculatingTotalService;
    }

    @PostMapping("/api/selling")
    public Map<String, Object> CalculatingTotalController() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("InPocket.json");
        InputStream inputStream = resource.getInputStream();
        JsonNode itemJsonnode = objectMapper.readTree(inputStream);

        return CalculatingTotalService.CalculatingTotalService(itemJsonnode);
    }
}
