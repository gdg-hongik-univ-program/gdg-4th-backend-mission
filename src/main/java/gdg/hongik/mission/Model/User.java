package gdg.hongik.mission.Model;


import gdg.hongik.mission.DTOs.DTOs;

public class User {

    private String userName;
    private Position userPosition;

    public User(String userName, Position userPosition) {
        this.userName = userName;
        this.userPosition = userPosition;
    }

    public String getUserName(){
        return this.userName;
    }

    public void setUserName(String userName){

        this.userName = userName;
    }

    public Position getUserPosition(){
        return this.userPosition;
    }
}
enum Position { CONSUMER, ADMIN };

