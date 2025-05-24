package gdg.hongik.mission.Controller;

import org.json.simple.JSONObject;

public class JSONController {
    public JSONObject setProductNameInJSON(String Name) {
        JSONObject productNameInJSON = new JSONObject();
        productNameInJSON.put("name", Name);
        return productNameInJSON;
    }
}
