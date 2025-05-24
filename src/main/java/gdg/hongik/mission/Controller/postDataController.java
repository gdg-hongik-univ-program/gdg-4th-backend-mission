package gdg.hongik.mission.Controller;

import gdg.hongik.mission.Service.SpringPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class postDataController {
    private final SpringPostService springPostService;

    public PostDataController(SpringPostService springPostService) { // 생성자
        this.springPostService = springPostService;
    }

    public JSONObject postDataSetting () throws Exception{
        JSONObject productName = springPostService.getUserInfo();
        return productName;
    }
/*
    @PostMapping("/postdata")
    @ResponseBody
    public JSONObject postMethod(@RequestBody JSONObject productName) throws Exception{
        productName = springPostService.getUserInfo();
        return productName;
    }
}??
*/