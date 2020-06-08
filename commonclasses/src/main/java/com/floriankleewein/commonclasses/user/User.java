package com.floriankleewein.commonclasses.user;

public class User {

    private String userName;
    private UserCards userCards;
    private GamePoints gamePoints;
    private boolean gameCreator = false;
    private boolean isCheater;

    public User() {
    }

    //constructor needed for user registration
    public User(String userName) {
        this.userName = userName;
        isCheater = false;
        setUpforGame();
    }

    /**@Author Maurer Florian
     * Instanced the user cards and the game points.
     */
    public void setUpforGame() {
        this.userCards = new UserCards();
        this.gamePoints = new GamePoints();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

