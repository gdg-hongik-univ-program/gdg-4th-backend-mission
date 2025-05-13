package gdg.hongik.mission.user;

public class User {
    private Long id;
    private String name;
    private String position;

    public User() {}

    public User(Long id, String name, String position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public String getPosition() {return position;}
    public void setId(Long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setPosition(String position) {this.position = position;}
}
