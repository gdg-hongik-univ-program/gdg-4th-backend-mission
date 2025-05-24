package gdg.hongik.mission.Service;

import gdg.hongik.mission.Controller.JSONController;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

@Service
public class SpringPostService {
    JSONController jsonController = new JSONController();

    public JSONObject getUserInfo() throws Exception {
        ClassPathResource resource = new ClassPathResource("userinfo.json");
        JSONObject userNameJSON = (JSONObject) new JSONParser().parse(new InputStreamReader(resource.getInputStream(), "UTF-8"));

        System.out.println(userNameJSON);

        return jsonController.setProductNameInJSON("test");
    }
}
