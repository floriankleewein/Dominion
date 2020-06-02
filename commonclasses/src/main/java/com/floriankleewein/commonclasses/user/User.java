package com.floriankleewein.commonclasses.user;


import com.floriankleewein.commonclasses.gamelogic.PlayStatus;

public class User {
    private static long id;
    private long userId;
    private String userName;
    private String userEmail;
    private String password;
    private UserCards userCards;
    private GamePoints gamePoints;
    private boolean gameCreator = false;
    private boolean isCheater;
    private PlayStatus playStatus;


    public User() {
    }

    //constructor needed for user registration
    public User(String userName) {
        this.userName = userName;
        isCheater = false;
        setUpforGame();
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public PlayStatus getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(PlayStatus playStatus) {
        this.playStatus = playStatus;
    }


}

