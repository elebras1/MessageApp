package com.elebras1.message.controller;

import com.elebras1.message.core.DataManager;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.BubbleTextIdentifyView;
import com.elebras1.message.ihm.view.ListElementView;

import java.util.ArrayList;
import java.util.List;

public class UsersController implements IUsersController, IChatObservable {
    private final DataManager dataManager;
    private final ListElementView view;
    private final List<IChatObserver> observers = new ArrayList<>();

    public UsersController(DataManager dataManager, ListElementView view) {
        this.dataManager = dataManager;
        this.view = view;
    }

    @Override
    public void addChatObserver(IChatObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeChatObserver(IChatObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyRecipientSelected(java.util.UUID recipientUuid) {
        for (IChatObserver observer : observers) {
            observer.onRecipientSelected(recipientUuid);
        }
    }

    @Override
    public void loadUsers(User connectedUser) {
        List<User> allUsers = new ArrayList<>(dataManager.getUsers());
        for (User user : allUsers) {
            BubbleTextIdentifyView bubble = new BubbleTextIdentifyView(user.getUuid(), user.getName());
            bubble.addOnClickListener(this::notifyRecipientSelected);
            view.addContent(bubble);
        }
    }
}
