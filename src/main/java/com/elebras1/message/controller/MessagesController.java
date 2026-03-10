package com.elebras1.message.controller;

import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.database.IDatabaseObserver;
import com.elebras1.message.datamodel.Channel;
import com.elebras1.message.datamodel.Message;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.MessageView;
import com.elebras1.message.ihm.view.MessagesView;
import com.elebras1.message.util.StringUtils;

import java.util.*;

public class MessagesController implements IMessagesController, ISelectionObserver, IDatabaseObserver {
    private final DataManager dataManager;
    private final MessagesView view;
    private User connectedUser;
    private UUID currentRecipientUuid;

    public MessagesController(DataManager dataManager, MessagesView view) {
        this.dataManager = dataManager;
        this.view = view;
    }

    @Override
    public void setConnectedUser(User connectedUser) {
        this.connectedUser = connectedUser;
    }

    @Override
    public void onRecipientSelected(UUID recipientUuid) {
        loadMessagesByRecipientUuid(recipientUuid);
    }

    @Override
    public void loadMessagesByRecipientUuid(UUID recipientUuid) {
        this.currentRecipientUuid = recipientUuid;
        view.clearMessages();

        List<Message> conversation = new ArrayList<>();
        conversation.addAll(dataManager.getMessagesFrom(connectedUser.getUuid(), recipientUuid));
        conversation.addAll(dataManager.getMessagesTo(connectedUser.getUuid(), recipientUuid));

        conversation.sort(Comparator.comparing(Message::getEmissionDate));

        for (Message message : conversation) {
            view.addMessage(new MessageView(message.getText(), message.getSender().getName() + " " + StringUtils.formatDate(message.getEmissionDate())));
        }
    }

    @Override
    public void sendMessage(String text) {
        if (text == null || text.trim().isEmpty()) {
            return;
        }
        if (connectedUser == null || currentRecipientUuid == null) {
            return;
        }

        Message message = new Message(connectedUser, currentRecipientUuid, text.trim());
        dataManager.sendMessage(message);
    }

    @Override
    public void notifyMessageAdded(Message addedMessage) {
        if(addedMessage.getRecipient().equals(currentRecipientUuid)) {
            this.loadMessagesByRecipientUuid(currentRecipientUuid);
        }
    }

    @Override
    public void notifyMessageDeleted(Message deletedMessage) {

    }

    @Override
    public void notifyMessageModified(Message modifiedMessage) {

    }

    @Override
    public void notifyUserAdded(User addedUser) {

    }

    @Override
    public void notifyUserDeleted(User deletedUser) {

    }

    @Override
    public void notifyUserModified(User modifiedUser) {

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
