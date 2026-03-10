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
        this.view.setOnAddChannelAction(this::onCreateChannel);
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
                ChannelView channelView = new ChannelView(
                        channel.getUuid(), channel.getName(), isCreator,
                        this::onRemoveChannel, this::onAddMemberRequested
                );
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
            dataManager.sendChannel(new Channel(channel.getUuid(), channel.getCreator(), channel.getName(), users));
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

    private void onAddMemberRequested(UUID channelUuid) {
        Channel channel = dataManager.getChannel(channelUuid);
        if (channel == null) return;

        List<User> members = new ArrayList<>(channel.getUsers());

        // Candidats = utilisateurs pas encore membres
        Set<User> allUsers = dataManager.getUsers();
        List<User> candidates = new ArrayList<>();
        for (User user : allUsers) {
            if (!channel.getUsers().contains(user)) {
                candidates.add(user);
            }
        }

        // Membres retirables = membres sauf le créateur
        User creator = channel.getCreator();
        List<User> removableMembers = new ArrayList<>();
        for (User member : members) {
            if (!member.equals(creator)) {
                removableMembers.add(member);
            }
        }

        view.showMembersDialog(
                channel.getName(),
                removableMembers,
                candidates,
                userToAdd -> {
                    List<User> updated = new ArrayList<>(channel.getUsers());
                    updated.add(userToAdd);
                    dataManager.sendChannel(new Channel(channel.getUuid(), creator, channel.getName(), updated));
                },
                userToRemove -> {
                    List<User> updated = new ArrayList<>(channel.getUsers());
                    updated.remove(userToRemove);
                    dataManager.sendChannel(new Channel(channel.getUuid(), creator, channel.getName(), updated));
                }
        );
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
