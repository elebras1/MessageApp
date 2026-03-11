package com.elebras1.message.controller.impl;

import com.elebras1.message.common.UiDispatcher;
import com.elebras1.message.controller.IUsersController;
import com.elebras1.message.controller.observer.ISelectionObservable;
import com.elebras1.message.controller.observer.ISelectionObserver;
import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.database.IDatabaseObserver;
import com.elebras1.message.core.session.ISession;
import com.elebras1.message.datamodel.Channel;
import com.elebras1.message.datamodel.Message;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.factory.ViewFactory;
import com.elebras1.message.ihm.view.IUserView;
import com.elebras1.message.ihm.view.IUsersView;
import java.util.ArrayList;
import java.util.List;

public class UsersController implements IUsersController, ISelectionObservable, IDatabaseObserver {
    private final DataManager dataManager;
    private final ISession session;
    private final IUsersView view;
    private final List<ISelectionObserver> observers;
    private final ViewFactory viewFactory;
    private final UiDispatcher uiDispatcher;

    public UsersController(DataManager dataManager, ISession session, IUsersView view, ViewFactory viewFactory, UiDispatcher uiDispatcher) {
        this.dataManager = dataManager;
        this.session = session;
        this.view = view;
        this.viewFactory = viewFactory;
        this.observers = new ArrayList<>();
        this.uiDispatcher = uiDispatcher;
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
        if (this.session.getConnectedUser() == null) {
            return;
        }
        view.clearUsers();
        List<User> allUsers = new ArrayList<>(dataManager.getUsers());
        for (User user : allUsers) {
            if(user.getUuid().equals(session.getConnectedUser().getUuid())) {
                continue;
            }
            IUserView userView = viewFactory.createUserView(user.getUuid(), user.getName() + " (@" + user.getUserTag() + ")", user.isOnline());
            userView.setOnClickCallback(this::notifyRecipientSelected);
            view.addUser(userView);
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
        uiDispatcher.dispatch(this::loadUsers);
    }

    @Override
    public void notifyUserDeleted(User deletedUser) {
        if(this.session.getConnectedUser() != null) {
            uiDispatcher.dispatch(this::loadUsers);
        }
    }

    @Override
    public void notifyUserModified(User modifiedUser) {
        uiDispatcher.dispatch(this::loadUsers);
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
