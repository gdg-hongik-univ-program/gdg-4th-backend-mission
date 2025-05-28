/**
 * @deprecated
 */

package gdg.hongik.mission.Service.Pockets;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PocketsSendingService {
    private final ReturningTotalService returningTotalService;
    public PocketsSendingService(ReturningTotalService returningTotalService) {
        this.returningTotalService = returningTotalService;
    }

    public Map<String, Object> CustomerSendInfo(JsonNode itemJsonnode) throws Exception {
        Map<String,Object> itemInformations = (Map<String,Object>) returningTotalService.ReturningTotalService (itemJsonnode);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String,Object>> request = new HttpEntity<>(itemInformations, headers);

        return itemInformations;
    }
}
