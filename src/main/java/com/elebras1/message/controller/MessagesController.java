package com.elebras1.message.controller;

import com.elebras1.message.core.DataManager;
import com.elebras1.message.datamodel.Message;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.BubbleTextView;
import com.elebras1.message.ihm.view.MessagesView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class MessagesController implements IMessagesController, IChatObserver {
    private final DataManager dataManager;
    private final MessagesView view;
    private User connectedUser;
    private UUID currentRecipientUuid;

    public MessagesController(DataManager dataManager, MessagesView view) {
        this.dataManager = dataManager;
        this.view = view;
    }

    @Override
    public void loadMessages(User connectedUser) {
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
            view.addMessage(new BubbleTextView(message.getText()));
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
        view.addMessage(new BubbleTextView(message.getText()));
    }
}
