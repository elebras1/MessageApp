package com.elebras1.message.controller.impl;

import com.elebras1.message.controller.ILoginController;
import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.session.ISession;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.exception.AuthenticationException;

import java.util.Set;

public class LoginController implements ILoginController {
    private final DataManager dataManager;
    private final ISession session;

    public LoginController(DataManager dataManager, ISession session) {
        this.dataManager = dataManager;
        this.session = session;
    }

    @Override
    public void login(String tag, String password) throws AuthenticationException {
        if (tag.isEmpty() || password.isEmpty()) {
            throw new AuthenticationException("Tous les champs doivent être remplis.");
        }

        Set<User> users = this.dataManager.getUsers();

        for (User user : users) {
            if (user.getUserTag().equals(tag)) {
                if (user.getUserPassword().equals(password)) {
                    user.setOnline(true);
                    this.dataManager.sendUser(user);
                    this.session.connect(user);
                    return;
                } else {
                    throw new AuthenticationException("Mot de passe incorrect.");
                }
            }
        }

        throw new AuthenticationException("L'utilisateur '" + tag + "' est introuvable.");
    }
}