package com.elebras1.message.controller;

import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.database.IDatabaseObserver;
import com.elebras1.message.datamodel.Channel;
import com.elebras1.message.datamodel.Message;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.BubbleTextIdentifyView;
import com.elebras1.message.ihm.view.ListElementView;

import java.util.ArrayList;
import java.util.List;

public class UsersController implements IUsersController, ISelectionObservable, IDatabaseObserver {
    private final DataManager dataManager;
    private final ListElementView view;
    private final List<ISelectionObserver> observers = new ArrayList<>();

    public UsersController(DataManager dataManager, ListElementView view) {
        this.dataManager = dataManager;
        this.view = view;
    }

    @Override
    public void addSelectionObserver(ISelectionObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeSelectionObserver(ISelectionObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyRecipientSelected(java.util.UUID recipientUuid) {
        for (ISelectionObserver observer : observers) {
            observer.onRecipientSelected(recipientUuid);
        }
    }

    @Override
    public void loadUsers() {
        List<User> allUsers = new ArrayList<>(dataManager.getUsers());
        for (User user : allUsers) {
            BubbleTextIdentifyView bubble = new BubbleTextIdentifyView(user.getUuid(), user.getName());
            bubble.addOnClickListener(this::notifyRecipientSelected);
            view.addContent(bubble);
        }
    }

    @Override
    public void notifyMessageAdded(Message addedMessage) {

    }

    @Override
    public void notifyMessageDeleted(Message deletedMessage) {

    }

    @Override
    public void notifyMessageModified(Message modifiedMessage) {

    }

    @Override
    public void notifyUserAdded(User addedUser) {
        this.loadUsers();
    }

    @Override
    public void notifyUserDeleted(User deletedUser) {
        this.loadUsers();
    }

    @Override
    public void notifyUserModified(User modifiedUser) {
        this.loadUsers();
    }

    @Override
    public void notifyChannelAdded(Channel addedChannel) {

    }

    @Override
    public void notifyChannelDeleted(Channel deletedChannel) {

    }

    @Override
    public void notifyChannelModified(Channel modifiedChannel) {

    }
}
