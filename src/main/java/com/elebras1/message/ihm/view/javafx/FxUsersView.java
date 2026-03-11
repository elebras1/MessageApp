package com.elebras1.message.ihm.view.javafx;

import com.elebras1.message.ihm.view.IUserView;
import com.elebras1.message.ihm.view.IUsersView;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;

public class FxUsersView extends BorderPane implements IUsersView {

    private final FxListElementView usersList;
    private final FxSearchBarView searchBar;
    private final List<IUserView> allUsers = new ArrayList<>();

    public FxUsersView() {
        searchBar = new FxSearchBarView("Rechercher un utilisateur...");
        usersList = new FxListElementView();

        setTop(searchBar);
        setCenter(usersList);

        searchBar.setOnSearchChanged(this::filterUsers);
    }

    private void filterUsers(String query) {
        usersList.clearContent();
        String lq = query.toLowerCase().trim();
        for (IUserView userView : allUsers) {
            if (lq.isEmpty() || userView.getText().toLowerCase().contains(lq)) {
                usersList.addContent((FxUserView) userView);
            }
        }
    }

    @Override
    public void clearUsers() {
        allUsers.clear();
        usersList.clearContent();
        searchBar.clear();
    }

    @Override
    public void addUser(IUserView userView) {
        allUsers.add(userView);
        String query = searchBar.getText();
        if (query.isEmpty() || userView.getText().toLowerCase().contains(query.toLowerCase())) {
            usersList.addContent((FxUserView) userView);
        }
    }
}

