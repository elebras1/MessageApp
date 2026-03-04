package com.elebras1.message.controller;

import com.elebras1.message.core.DataManager;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.BubbleTextIdentifyView;
import com.elebras1.message.ihm.view.ListBubbleTextView;

import java.util.ArrayList;
import java.util.List;

public class UsersController implements IUsersController {
    private final DataManager dataManager;
    private final ListBubbleTextView view;
    private final MessagesController messagesController;

    public UsersController(DataManager dataManager, ListBubbleTextView view, MessagesController messagesController) {
        this.dataManager = dataManager;
        this.view = view;
        this.messagesController = messagesController;
    }

    @Override
    public void loadUsers(User connectedUser) {
        List<User> allUsers = new ArrayList<>(dataManager.getUsers());
        for (User user : allUsers) {
            BubbleTextIdentifyView bubble = new BubbleTextIdentifyView(user.getUuid(), user.getName());
            bubble.addOnClickListener(messagesController::loadMessagesByRecipientUuid);
            view.addContent(bubble);
        }
    }
}
