package com.elebras1.message.controller;

import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.database.IDatabaseObserver;
import com.elebras1.message.core.session.ISession;
import com.elebras1.message.datamodel.Channel;
import com.elebras1.message.datamodel.Message;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.MessageView;
import com.elebras1.message.ihm.view.MessagesView;
import com.elebras1.message.util.StringUtils;

import javax.swing.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessagesController implements IMessagesController, ISelectionObserver, IDatabaseObserver {
    private final DataManager dataManager;
    private final MessagesView view;
    private final ISession session;
    private UUID currentRecipientUuid;

    public MessagesController(DataManager dataManager, ISession session, MessagesView view) {
        this.dataManager = dataManager;
        this.session = session;
        this.view = view;
    }

    @Override
    public void onRecipientSelected(UUID recipientUuid) {
        SwingUtilities.invokeLater(() -> loadMessagesByRecipientUuid(recipientUuid));
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
            view.addMessage(new MessageView(message.getText(), message.getSender().getName() + " " + StringUtils.formatDate(message.getEmissionDate())));
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

        List<String> userTags = this.extractTags(addedMessage.getText());

        boolean isChannelMessage = addedMessage.getRecipient().equals(currentRecipientUuid);
        boolean isDirectMessage = addedMessage.getRecipient().equals(this.session.getConnectedUser().getUuid()) && addedMessage.getSender().getUuid().equals(currentRecipientUuid);

        if (isChannelMessage || isDirectMessage) {
            SwingUtilities.invokeLater(() -> this.loadMessagesByRecipientUuid(currentRecipientUuid));
        }
    }

    private List<String> extractTags(String text) {
        List<String> tags = new ArrayList<>();
        Matcher matcher = Pattern.compile("@(\\w+)").matcher(text);
        while (matcher.find()) {
            tags.add(matcher.group(1));
        }
        return tags;
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
        if(this.session.getConnectedUser() != null) {
            this.loadMessagesByRecipientUuid(this.session.getConnectedUser().getUuid());
        }
    }

    @Override
    public void notifyUserModified(User modifiedUser) {
        this.loadMessagesByRecipientUuid(this.session.getConnectedUser().getUuid());
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
