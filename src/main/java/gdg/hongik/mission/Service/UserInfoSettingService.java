package gdg.hongik.mission.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gdg.hongik.mission.Domain.UserInfo;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.InputStream;

@Component
public class UserInfoSettingService {
    private final UserInfo userInfo;
    public UserInfoSettingService(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @PostConstruct
    public void getUserName() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("userInfo.json");
        InputStream inputStream = resource.getInputStream();
        JsonNode UserNode = objectMapper.readTree(inputStream);

        userInfo.setName(UserNode.get("name").asText());
        userInfo.setPosition(UserNode.get("position").asText());
    }

}
