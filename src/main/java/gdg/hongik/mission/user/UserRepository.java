package gdg.hongik.mission.user;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {

    private final Map<String, User> users = new HashMap<>();    // 요청을 이름과 포지션으로 구별해야 하므로
    private Long idCounter = 1L;

    public User save(String name, String position) {
        if(users.containsKey(name)) return users.get(name);
        User user = new User(idCounter++, name, position);
        users.put(name, user);
        return user;
    }

    public Optional<User> findByName(String name) {
        return Optional.ofNullable(users.get(name));    //NULL 반환 가능성
    }


}
