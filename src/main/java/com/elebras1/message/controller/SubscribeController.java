package com.elebras1.message.controller;

import com.elebras1.message.core.DataManager;
import com.elebras1.message.datamodel.User;

import java.util.Set;

public class SubscribeController implements ISubscribeController {
    private final DataManager dataManager;

    public SubscribeController(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void subscribe(String username, String tag, String password) {
        if (username.isEmpty() || tag.isEmpty() || password.isEmpty()) {
            System.out.println("Tous les champs doivent être remplis.");
            return;
        }

        User userToSubscribe = new User(tag, password, username);

        Set<User> users = dataManager.getUsers();
        for(User user : users) {
            if(user.getUserTag().equals(tag)) {
                System.out.println("Ce tag est déjà utilisé.");
                return;
            }
        }
        this.dataManager.sendUser(userToSubscribe);
    }
}
