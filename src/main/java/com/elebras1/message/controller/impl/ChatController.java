package com.elebras1.message.controller.impl;

import com.elebras1.message.common.UiDispatcher;
import com.elebras1.message.controller.IChatController;
import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.session.ISession;
import com.elebras1.message.core.session.ISessionObserver;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.factory.ViewFactory;
import com.elebras1.message.ihm.view.IChatView;
import com.elebras1.message.ihm.view.IChannelsView;
import com.elebras1.message.ihm.view.IMessageAppMainView;
import com.elebras1.message.ihm.view.IMessagesView;
import com.elebras1.message.ihm.view.IUsersView;

public class ChatController implements IChatController, ISessionObserver {
    private final IChatView view;
    private final DataManager dataManager;
    private final ISession session;
    private final IMessageAppMainView mainView;
    private final ViewFactory viewFactory;
    private final UiDispatcher uiDispatcher;

    public ChatController(IChatView view, DataManager dataManager, ISession session,
                          IMessageAppMainView mainView, ViewFactory viewFactory, UiDispatcher uiDispatcher) {
        this.view = view;
        this.dataManager = dataManager;
        this.session = session;
        this.mainView = mainView;
        this.viewFactory = viewFactory;
        this.uiDispatcher = uiDispatcher;
    }

    @Override
    public void notifyLogin(User connectedUser) {
        IMessagesView messagesView = viewFactory.createMessagesView();
        MessagesController listMessageController = new MessagesController(dataManager, session, messagesView, viewFactory, uiDispatcher);
        dataManager.addObserver(listMessageController);
        messagesView.setSendAction(listMessageController::sendMessage);
        view.setRightSection(messagesView);

        IUsersView usersView = viewFactory.createUsersView();
        UsersController listUserController = new UsersController(dataManager, session, usersView, viewFactory, uiDispatcher);
        listUserController.addSelectionObserver(listMessageController);
        listUserController.loadUsers();
        dataManager.addObserver(listUserController);
        view.setLeftUpSection(usersView);

        IChannelsView channelsView = viewFactory.createChannelsView();
        ChannelsController listChannelController = new ChannelsController(dataManager, session, channelsView, viewFactory, uiDispatcher);
        listChannelController.addSelectionObserver(listMessageController);
        listChannelController.loadChannels(connectedUser);
        dataManager.addObserver(listChannelController);
        view.setLeftDownSection(channelsView);

        mainView.addPanel(view);
    }

    @Override
    public void notifyLogout() {
        mainView.removeCurrentPanel();
    }
}
