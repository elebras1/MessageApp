package com.elebras1.message.controller.impl;

import com.elebras1.message.controller.IMessagesController;
import com.elebras1.message.controller.observer.ISelectionObserver;
import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.database.IDatabaseObserver;
import com.elebras1.message.core.session.ISession;
import com.elebras1.message.datamodel.Channel;
import com.elebras1.message.datamodel.Message;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.common.UiDispatcher;
import com.elebras1.message.factory.ViewFactory;
import com.elebras1.message.ihm.view.IMessageView;
import com.elebras1.message.ihm.view.IMessagesView;
import com.elebras1.message.util.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessagesController implements IMessagesController, ISelectionObserver, IDatabaseObserver {
    private final DataManager dataManager;
    private final IMessagesView view;
    private final ISession session;
    private final ViewFactory viewFactory;
    private final UiDispatcher uiDispatcher;
    private UUID currentRecipientUuid;

    public MessagesController(DataManager dataManager, ISession session, IMessagesView view, ViewFactory viewFactory, UiDispatcher uiDispatcher) {
        this.dataManager = dataManager;
        this.session = session;
        this.view = view;
        this.viewFactory = viewFactory;
        this.uiDispatcher = uiDispatcher;
    }

    @Override
    public void onRecipientSelected(UUID recipientUuid) {
        uiDispatcher.dispatch(() -> loadMessagesByRecipientUuid(recipientUuid));
    }

    @Override
    public void loadMessagesByRecipientUuid(UUID recipientUuid) {
        this.currentRecipientUuid = recipientUuid;
        view.clearMessages();

        List<Message> conversation = new ArrayList<>();

        Channel channel = dataManager.getChannel(recipientUuid);
        if (channel != null) {
            conversation.addAll(dataManager.getMessagesTo(recipientUuid));
        } else {
            conversation.addAll(dataManager.getMessagesFrom(this.session.getConnectedUser().getUuid(), recipientUuid));
            conversation.addAll(dataManager.getMessagesTo(this.session.getConnectedUser().getUuid(), recipientUuid));
        }

        conversation.sort(Comparator.comparing(Message::getEmissionDate));

        for (Message message : conversation) {
            boolean isMine = message.getSender().getUuid().equals(this.session.getConnectedUser().getUuid());
            IMessageView mv = viewFactory.createMessageView(
                    message.getUuid(),
                    message.getText(),
                    message.getSender().getName() + " " + StringUtils.formatDate(message.getEmissionDate()),
                    isMine
            );
            mv.setOnDeleteCallback(this::onDeleteMessage);
            view.addMessage(mv);
        }
    }

    @Override
    public void sendMessage(String text) {
        if (text == null || text.trim().isEmpty()) {
            return;
        }
        if (this.session.getConnectedUser() == null || currentRecipientUuid == null) {
            return;
        }

        Message message = new Message(this.session.getConnectedUser(), currentRecipientUuid, text.trim());
        dataManager.sendMessage(message);
    }

    @Override
    public void notifyMessageAdded(Message addedMessage) {
        if (currentRecipientUuid == null || this.session.getConnectedUser() == null) {
            return;
        }

        boolean isChannelMessage = addedMessage.getRecipient().equals(currentRecipientUuid);
        boolean isDirectMessage = addedMessage.getRecipient().equals(this.session.getConnectedUser().getUuid()) && addedMessage.getSender().getUuid().equals(currentRecipientUuid);

        if (isChannelMessage || isDirectMessage) {
            uiDispatcher.dispatch(() -> this.loadMessagesByRecipientUuid(currentRecipientUuid));
        }
    }

    @Override
    public void onDeleteMessage(UUID messageUuid) {
        dataManager.getMessages().stream()
                .filter(m -> m.getUuid().equals(messageUuid))
                .findFirst()
                .ifPresent(dataManager::deleteMessage);
    }

    @Override
    public void notifyMessageDeleted(Message deletedMessage) {
        if (currentRecipientUuid != null) {
            uiDispatcher.dispatch(() -> this.loadMessagesByRecipientUuid(currentRecipientUuid));
        }
    }

    @Override
    public void notifyMessageModified(Message modifiedMessage) {

    }

    @Override
    public void notifyUserAdded(User addedUser) {

    }

    @Override
    public void notifyUserDeleted(User deletedUser) {
        if(this.session.getConnectedUser() != null) {
            uiDispatcher.dispatch(() -> this.loadMessagesByRecipientUuid(this.session.getConnectedUser().getUuid()));
        }
    }

    @Override
    public void notifyUserModified(User modifiedUser) {
        if (this.session.getConnectedUser() != null) {
            uiDispatcher.dispatch(() -> this.loadMessagesByRecipientUuid(this.session.getConnectedUser().getUuid()));
        }
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
