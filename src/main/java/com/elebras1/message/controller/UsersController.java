package com.elebras1.message.controller;

import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.database.IDatabaseObserver;
import com.elebras1.message.core.session.ISession;
import com.elebras1.message.datamodel.Channel;
import com.elebras1.message.datamodel.Message;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.BubbleTextIdentifyView;
import com.elebras1.message.ihm.view.ListElementView;
import com.elebras1.message.ihm.view.UsersView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class UsersController implements IUsersController, ISelectionObservable, IDatabaseObserver {
    private final DataManager dataManager;
    private final ISession session;
    private final UsersView view;
    private final List<ISelectionObserver> observers;

    public UsersController(DataManager dataManager, ISession session, UsersView view) {
        this.dataManager = dataManager;
        this.session = session;
        this.view = view;
        this.observers = new ArrayList<>();
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
        view.clearUsers();
        List<User> allUsers = new ArrayList<>(dataManager.getUsers());
        for (User user : allUsers) {
            if(user.getUuid().equals(session.getConnectedUser().getUuid())) {
                continue;
            }
            BubbleTextIdentifyView bubble = new BubbleTextIdentifyView(user.getUuid(), user.getName() + " (@" + user.getUserTag() + ")");
            bubble.setOnClickCallback(this::notifyRecipientSelected);
            view.addUser(bubble);
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
        SwingUtilities.invokeLater(this::loadUsers);
    }

    @Override
    public void notifyUserDeleted(User deletedUser) {
        if(this.session.getConnectedUser() != null) {
            SwingUtilities.invokeLater(this::loadUsers);
        }
    }

    @Override
    public void notifyUserModified(User modifiedUser) {
        SwingUtilities.invokeLater(this::loadUsers);
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
