package com.elebras1.message.controller;

import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.session.ISessionObserver;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.ChatView;
import com.elebras1.message.ihm.view.ListBubbleTextView;
import com.elebras1.message.ihm.view.MessageAppMainView;
import com.elebras1.message.ihm.view.MessagesView;

public class ChatController implements IChatController, ISessionObserver {
    private final ChatView view;
    private final DataManager dataManager;
    private final MessageAppMainView mainView;


    public ChatController(ChatView view, DataManager dataManager, MessageAppMainView mainView) {
        this.view = view;
        this.dataManager = dataManager;
        this.mainView = mainView;
    }

    @Override
    public void notifyLogin(User connectedUser) {
        MessagesView messagesView = new MessagesView();
        MessagesController listMessageController = new MessagesController(dataManager, messagesView);
        listMessageController.loadMessages(connectedUser);
        messagesView.setSendAction(listMessageController::sendMessage);
        view.setRightSection(messagesView);

        ListBubbleTextView usersView = new ListBubbleTextView();
        UsersController listUserController = new UsersController(dataManager, usersView, listMessageController);
        listUserController.loadUsers(connectedUser);
        view.setLeftUpSection(usersView);

        ListBubbleTextView channelView = new ListBubbleTextView();
        ChannelsController listChannelController = new ChannelsController(dataManager, channelView, listMessageController);
        listChannelController.loadChannels(connectedUser);
        view.setLeftDownSection(channelView);


        mainView.addPanel(view);
    }

    @Override
    public void notifyLogout() {
        mainView.removeCurrentPanel();
    }
}
