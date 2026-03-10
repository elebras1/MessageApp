package com.elebras1.message.controller;

import com.elebras1.message.core.DataManager;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.exception.RegistrationException;

import java.util.Set;

public class SubscribeController implements ISubscribeController {
    private final DataManager dataManager;

    public SubscribeController(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void subscribe(String username, String tag, String password) throws RegistrationException {
        if (username.isEmpty() || tag.isEmpty() || password.isEmpty()) {
            throw new RegistrationException("Tous les champs doivent être remplis.");
        }

        Set<User> users = dataManager.getUsers();
        for (User user : users) {
            if (user.getUserTag().equals(tag)) {
                throw new RegistrationException("Ce tag est déjà utilisé.");
            }
        }

        User userToSubscribe = new User(tag, password, username);
        this.dataManager.sendUser(userToSubscribe);
    }
}