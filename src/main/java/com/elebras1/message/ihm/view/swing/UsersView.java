package com.elebras1.message.ihm.view.swing;

import com.elebras1.message.ihm.view.IUserView;
import com.elebras1.message.ihm.view.IUsersView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UsersView extends JPanel implements IUsersView {
    private final ListElementView usersList;
    private final SearchBarView searchBar;
    private final List<IUserView> allUsers = new ArrayList<>();

    public UsersView() {
        this.setLayout(new BorderLayout());
        this.searchBar = new SearchBarView("Rechercher un utilisateur...");
        this.usersList = new ListElementView();

        this.add(this.searchBar, BorderLayout.NORTH);
        this.add(this.usersList, BorderLayout.CENTER);

        this.searchBar.setOnSearchChanged(this::filterUsers);
    }

    private void filterUsers(String query) {
        this.usersList.clearContent();
        String lowerQuery = query.toLowerCase().trim();
        for (IUserView userView : allUsers) {
            if (lowerQuery.isEmpty() || userView.getText().toLowerCase().contains(lowerQuery)) {
                this.usersList.addContent((JPanel) userView);
            }
        }
    }

    @Override
    public void clearUsers() {
        this.allUsers.clear();
        this.usersList.clearContent();
        this.searchBar.clear();
    }

    @Override
    public void addUser(IUserView userView) {
        this.allUsers.add(userView);
        String query = this.searchBar.getText();
        if (query.isEmpty() || userView.getText().toLowerCase().contains(query.toLowerCase())) {
            this.usersList.addContent((JPanel) userView);
        }
    }
}
