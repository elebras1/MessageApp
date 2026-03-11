package com.elebras1.message.ihm.view;

import com.elebras1.message.ihm.view.swing.UserView;

public interface IUsersView extends View {
    void clearUsers();

    void addUser(UserView userView);
}
