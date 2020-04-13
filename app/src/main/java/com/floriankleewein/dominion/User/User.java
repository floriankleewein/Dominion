package com.floriankleewein.dominion.User;

/**
 * Class User
 * The Lists should have the params Card
 */


public class User {
    static long ID;
    private long UserID;
    private String UserName;
    private String UserEmail;
    private String Password;
    private UserCards userCards;
    private GamePoints gamePoints;


    User(String UserName, String UserEmail, String Password) {
        this.UserID = ID++;
        this.UserName = UserName;
        this.UserEmail = UserEmail;
        this.Password = Password;
    }

    public GamePoints getGamePoints() {
        return gamePoints;
    }

    public void setGamePoints(GamePoints gamePoints) {
        this.gamePoints = gamePoints;
    }

    public UserCards getUserCards() {
        return userCards;
    }

    public void setUserCards(UserCards userCards) {
        this.userCards = userCards;
    }

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }


}


