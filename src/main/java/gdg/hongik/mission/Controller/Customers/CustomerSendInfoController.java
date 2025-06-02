/**
 * 재고 검색 기능 컨트롤러
 * postmapping 역할만 수행함
 *
 * @Author 김도현(dx2d2y)
 * @version 0.1
 */

package gdg.hongik.mission.Controller.Customers;

import gdg.hongik.mission.Service.UserSend.DataSendingService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CustomerSendInfoController {
    private final DataSendingService dataSendingService;
    public CustomerSendInfoController(DataSendingService dataSendingService) {
        this.dataSendingService = dataSendingService;
    }

    @PostMapping("/api/postInfos/{itemName}")
    public Map<String, Object> CustomerSendInfoController(@PathVariable String itemName) throws Exception {
        return dataSendingService.CustomerSendInfo(itemName);
    }
}
