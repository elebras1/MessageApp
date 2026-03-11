package com.elebras1.message.controller.impl;

import com.elebras1.message.controller.IChatController;
import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.session.ISession;
import com.elebras1.message.core.session.ISessionObserver;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.IMessageAppMainView;
import com.elebras1.message.ihm.view.swing.ChannelsView;
import com.elebras1.message.ihm.view.swing.ChatView;
import com.elebras1.message.ihm.view.swing.MessagesView;
import com.elebras1.message.ihm.view.swing.UsersView;

public class ChatController implements IChatController, ISessionObserver {
    private final ChatView view;
    private final DataManager dataManager;
    private final ISession session;
    private final IMessageAppMainView mainView;


    public ChatController(ChatView view, DataManager dataManager, ISession session, IMessageAppMainView mainView) {
        this.view = view;
        this.dataManager = dataManager;
        this.session = session;
        this.mainView = mainView;
    }

    @Override
    public void notifyLogin(User connectedUser) {
        MessagesView messagesView = new MessagesView();
        MessagesController listMessageController = new MessagesController(dataManager, session, messagesView);
        dataManager.addObserver(listMessageController);
        messagesView.setSendAction(listMessageController::sendMessage);
        view.setRightSection(messagesView);

        UsersView usersView = new UsersView();
        UsersController listUserController = new UsersController(dataManager, session, usersView);
        listUserController.addSelectionObserver(listMessageController);
        listUserController.loadUsers();
        dataManager.addObserver(listUserController);
        view.setLeftUpSection(usersView);

        ChannelsView channelView = new ChannelsView();
        ChannelsController listChannelController = new ChannelsController(dataManager,  session, channelView);
        listChannelController.addSelectionObserver(listMessageController);
        listChannelController.loadChannels(connectedUser);
        dataManager.addObserver(listChannelController);
        view.setLeftDownSection(channelView);

        mainView.addPanel(view);
    }

    @Override
    public void notifyLogout() {
        mainView.removeCurrentPanel();
    }
}
