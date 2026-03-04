package com.elebras1.message.controller;

import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.session.ISession;
import com.elebras1.message.core.session.Session;
import com.elebras1.message.datamodel.User;

import java.util.Set;

public class LoginController implements ILoginController {
    private final DataManager dataManager;
    private final ISession session;

    public LoginController(DataManager dataManager, ISession session) {
        this.dataManager = dataManager;
        this.session = session;
    }


    @Override
    public void login(String tag, String password) {
        if(password.isEmpty() || tag.isEmpty()) {
            System.out.println("Tous les champs doivent être remplis.");
            return;
        }

        Set<User> users = this.dataManager.getUsers();
        for(User user : users) {
            if(user.getUserTag().equals(tag) && user.getUserPassword().equals(password)) {
                user.setOnline(true);
                this.session.connect(user);
            }
        }
    }
}
