package gdg.hongik.mission.user;

import org.springframework.stereotype.Service;

/**
 * 사용자 관리 비즈니스 로직을 처리하는 서비스 클래스입니다.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 사용자 등록.
     *
     * @param name 사용자 이름
     * @param position 사용자 역할 (ADMIN or CONSUMER)
     * @return 등록된 사용자 객체
     * @throws IllegalArgumentException 이미 존재하는 사용자일 경우
     */
    public User registerUser(String name, String position) {
        if (userRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }
        return userRepository.save(new User(name, position));
    }

    /**
     * 사용자 이름으로 사용자 조회.
     *
     * @param name 사용자 이름
     * @return 사용자 객체
     * @throws IllegalArgumentException 사용자를 찾을 수 없는 경우
     */
    public User findByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    /**
     * 주어진 사용자 이름으로 관리자인지 확인합니다.
     *
     * @param username 확인할 사용자 이름
     * @return 관리자인 경우 true, 아니면 false
     * @throws IllegalArgumentException 해당 사용자가 존재하지 않는 경우
     */
    public boolean isAdmin(String username) {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
        return "ADMIN".equalsIgnoreCase(user.getPosition());
    }

}
