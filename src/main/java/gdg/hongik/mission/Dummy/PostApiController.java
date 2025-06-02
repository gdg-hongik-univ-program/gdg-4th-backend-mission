package gdg.hongik.mission.Dummy;

import org.springframework.web.bind.annotation.*;

@RestController
public class PostApiController {
    private final GivingItemInfoService givingItemInfoService;
    public PostApiController(GivingItemInfoService givingItemInfoService) {
        this.givingItemInfoService = givingItemInfoService;
    }

    @GetMapping("/api/postInfos/{itemName}")
    public String receiveInfo(@PathVariable String itemName) throws Exception {
        return givingItemInfoService.givingItemInfo(itemName);
    }
}