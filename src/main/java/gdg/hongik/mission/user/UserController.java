package gdg.hongik.mission.user;

import gdg.hongik.mission.user.dto.UserRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 사용자 관련 요청을 처리하는 컨트롤러 클래스입니다.
 */
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 사용자 등록 API.
     *
     * @param request 사용자 등록 요청 DTO
     * @return 등록된 사용자 정보
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserRequestDTO request) {
        User user = userService.registerUser(request.getName(), request.getPosition());
        return ResponseEntity.ok(user);
    }
}
