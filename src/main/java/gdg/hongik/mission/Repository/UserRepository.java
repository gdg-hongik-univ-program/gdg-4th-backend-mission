package gdg.hongik.mission.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gdg.hongik.mission.Domain.UserInfo;
import org.json.simple.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.InputStream;

@Repository
public class UserRepository {
    private final UserInfo userInfo;
    private String username;
    private String position;

    public UserRepository(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public JSONObject getUserJSON() throws Exception {
        JSONObject userInformation = new JSONObject();
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("userInfo.json");
        InputStream inputStream = resource.getInputStream();

        JsonNode root = objectMapper.readTree(inputStream);
        username = root.get("name").asText();
        position = root.get("position").asText();

        System.out.println(username);
        System.out.println(position);

        userInfo.setName(username);
        userInfo.setPosition(position);

        userInformation.put("name", username);
        userInformation.put("position", position);

        return userInformation;
    }
}
