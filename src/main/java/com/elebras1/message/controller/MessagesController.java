package com.elebras1.message.controller;

import com.elebras1.message.core.DataManager;
import com.elebras1.message.datamodel.Message;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.BubbleTextView;
import com.elebras1.message.ihm.view.MessagesView;

import java.util.Comparator;
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

        dataManager.getMessages().stream()
                .filter(message -> isConversationMessage(message, recipientUuid))
                .sorted(Comparator.comparing(Message::getEmissionDate))
                .map(message -> new BubbleTextView(message.getText()))
                .forEach(view::addMessage);
    }

    private boolean isConversationMessage(Message message, UUID recipientUuid) {
        UUID sender = message.getSender().getUuid();
        UUID recipient = message.getRecipient();
        UUID currentUserUuid = connectedUser.getUuid();

        boolean sentByMe = currentUserUuid.equals(sender) && recipientUuid.equals(recipient);
        boolean receivedByMe = recipientUuid.equals(sender) && currentUserUuid.equals(recipient);

        return sentByMe || receivedByMe;
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
