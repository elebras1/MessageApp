package com.elebras1.message.ihm.view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UsersView extends JPanel {
    private final ListElementView usersList;
    private final SearchBarView searchBar;
    private final List<BubbleTextIdentifyView> allUsers = new ArrayList<>();

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
        for (BubbleTextIdentifyView userView : allUsers) {
            if (lowerQuery.isEmpty() || userView.getText().toLowerCase().contains(lowerQuery)) {
                this.usersList.addContent(userView);
            }
        }
    }

    public void clearUsers() {
        this.allUsers.clear();
        this.usersList.clearContent();
        this.searchBar.clear();
    }

    public void addUser(BubbleTextIdentifyView userView) {
        this.allUsers.add(userView);
        String query = this.searchBar.getText();
        if (query.isEmpty() || userView.getText().toLowerCase().contains(query.toLowerCase())) {
            this.usersList.addContent(userView);
        }
    }
}
