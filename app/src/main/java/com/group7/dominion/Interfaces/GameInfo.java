package com.group7.dominion.Interfaces;

import com.group7.dominion.User.User;

public interface GameInfo {
    // Interface for GameInfo Object to update Clients between Player actions
    // TODO Methods not final
    void updateServer();
    void updateClient();
    void setUser(User user);
    User getUser();
    String getMessage();
    void setMessage(String message);
}
