package gdg.hongik.mission.Domain;

import org.springframework.stereotype.Component;

@Component
public class UserInfo {
    private String name;
    private String position;

    public String getName() {
        return name;
    }
    public String getPosition() {
        return position;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPosition(String position) {
        this.position = position;
    }
}
