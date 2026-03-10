package com.elebras1.message.controller;

import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.database.IDatabaseObserver;
import com.elebras1.message.core.session.ISession;
import com.elebras1.message.datamodel.Channel;
import com.elebras1.message.datamodel.Message;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.ChannelView;
import com.elebras1.message.ihm.view.ChannelsView;

import javax.swing.*;
import java.util.*;

public class ChannelsController implements IChannelsController, ISelectionObservable, IDatabaseObserver {
    private final DataManager dataManager;
    private final ISession session;
    private final ChannelsView view;
    private final List<ISelectionObserver> observers;

    public ChannelsController(DataManager dataManager, ISession session, ChannelsView view) {
        this.dataManager = dataManager;
        this.session = session;
        this.view = view;
        this.observers = new ArrayList<>();
        this.view.setOnAddChannelAction(this::showCreateChannelDialog);
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
    public void notifyRecipientSelected(UUID recipientUuid) {
        for (ISelectionObserver observer : observers) {
            observer.onRecipientSelected(recipientUuid);
        }
    }

    @Override
    public void loadChannels(User connectedUser) {
        List<Channel> allChannels = new ArrayList<>(dataManager.getChannels());
        view.clearChannels();
        for (Channel channel : allChannels) {
            if (channel.getUsers().contains(connectedUser)) {
                boolean isCreator = channel.getCreator().equals(connectedUser);
                ChannelView channelView = new ChannelView(channel.getUuid(), channel.getName(), isCreator, this::onRemoveChannel, this::showAddMemberDialog);
                channelView.setOnClickCallback(this::notifyRecipientSelected);
                view.addChannel(channelView);
            }
        }
    }

    private void onRemoveChannel(UUID channelUuid) {
        Channel channel = dataManager.getChannel(channelUuid);
        if (channel == null) {
            return;
        }

        User connectedUser = session.getConnectedUser();
        if (channel.getCreator().equals(connectedUser)) {
            dataManager.deleteChannel(channel);
        } else {
            List<User> users = new ArrayList<>(channel.getUsers());
            users.remove(connectedUser);
            Channel updatedChannel = new Channel(channel.getUuid(), channel.getCreator(), channel.getName(), users);
            dataManager.sendChannel(updatedChannel);
        }
    }

    private void onCreateChannel(String channelName) {
        if (channelName == null || channelName.trim().isEmpty()) {
            return;
        }
        User creator = session.getConnectedUser();
        Channel channel = new Channel(creator, channelName.trim(), List.of(creator));
        dataManager.sendChannel(channel);
    }

    private void showCreateChannelDialog() {
        String channelName = JOptionPane.showInputDialog(view, "Nom du nouveau channel :", "Créer un channel", JOptionPane.PLAIN_MESSAGE);
        if (channelName != null && !channelName.trim().isEmpty()) {
            onCreateChannel(channelName.trim());
        }
    }

    private void showAddMemberDialog(UUID channelUuid) {
        Channel channel = dataManager.getChannel(channelUuid);
        if (channel == null) {
            return;
        }

        Set<User> allUsers = dataManager.getUsers();
        List<User> candidates = new ArrayList<>();
        for (User user : allUsers) {
            if (!channel.getUsers().contains(user)) {
                candidates.add(user);
            }
        }

        if (candidates.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Tous les utilisateurs sont déjà membres de ce channel.", "Ajouter un membre", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] userNames = candidates.stream()
                .map(u -> u.getName() + " (@" + u.getUserTag() + ")")
                .toArray(String[]::new);

        String selected = (String) JOptionPane.showInputDialog(view, "Choisir un utilisateur à ajouter :", "Ajouter un membre", JOptionPane.PLAIN_MESSAGE, null, userNames, userNames[0]);

        if (selected != null) {
            int index = Arrays.asList(userNames).indexOf(selected);
            if (index >= 0) {
                User userToAdd = candidates.get(index);
                List<User> updatedUsers = new ArrayList<>(channel.getUsers());
                updatedUsers.add(userToAdd);
                Channel updatedChannel = new Channel(channel.getUuid(), channel.getCreator(), channel.getName(), updatedUsers);
                dataManager.sendChannel(updatedChannel);
            }
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
    }

    @Override
    public void notifyUserDeleted(User deletedUser) {
    }

    @Override
    public void notifyUserModified(User modifiedUser) {

    }

    @Override
    public void notifyChannelAdded(Channel addedChannel) {
        SwingUtilities.invokeLater(() -> this.loadChannels(session.getConnectedUser()));
    }

    @Override
    public void notifyChannelDeleted(Channel deletedChannel) {
        SwingUtilities.invokeLater(() -> this.loadChannels(session.getConnectedUser()));
    }

    @Override
    public void notifyChannelModified(Channel modifiedChannel) {
        SwingUtilities.invokeLater(() -> this.loadChannels(session.getConnectedUser()));
    }
}
