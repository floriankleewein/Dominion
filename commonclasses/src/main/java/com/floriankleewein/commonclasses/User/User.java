package com.floriankleewein.commonclasses.User;


import com.floriankleewein.commonclasses.CheatFunction.CheatService;

public class User {
    private static long ID;
    private long UserID;
    private String UserName;
    private String UserEmail;
    private String Password;
    private UserCards userCards;
    private GamePoints gamePoints;
    private boolean gameCreator = false;



    private boolean isCheater;


    public User(String UserName, String UserEmail, String Password) {
        this.UserID = ID++;
        this.UserName = UserName;
        this.UserEmail = UserEmail;
        this.Password = Password;
        isCheater = false;

    }
    public User(){
    }

    //constructor needed for user registration
    public User(String userName) {
        this.UserName = userName;
    }

    public void setUpforGame() {
        this.userCards = new UserCards();
        this.gamePoints = new GamePoints();
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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public boolean isGameCreator() {
        return gameCreator;
    }

    public void setGameCreator(boolean gameCreator) {
        this.gameCreator = gameCreator;
    }


    public boolean isCheater() {
        return isCheater;
    }

    public void setCheater(boolean cheater) {
        isCheater = cheater;
    }
}


