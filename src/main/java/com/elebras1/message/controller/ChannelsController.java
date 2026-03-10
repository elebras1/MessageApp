package com.elebras1.message.controller;

import com.elebras1.message.core.DataManager;
import com.elebras1.message.core.database.IDatabaseObserver;
import com.elebras1.message.core.session.ISession;
import com.elebras1.message.datamodel.Channel;
import com.elebras1.message.datamodel.Message;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.ChannelView;
import com.elebras1.message.ihm.view.ListElementView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChannelsController implements IChannelsController, ISelectionObservable, IDatabaseObserver {
    private final DataManager dataManager;
    private final ISession session;
    private final ListElementView view;
    private final List<ISelectionObserver> observers;

    public ChannelsController(DataManager dataManager, ISession session, ListElementView view) {
        this.dataManager = dataManager;
        this.session = session;
        this.view = view;
        this.observers = new ArrayList<>();
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
        view.clearContent();
        for (Channel channel : allChannels) {
            if (channel.getUsers().contains(connectedUser)) {
                ChannelView channelView = new ChannelView(channel.getUuid(), channel.getName(), this::onRemoveChannel);
                channelView.addOnClickListener(this::notifyRecipientSelected);
                view.addContent(channelView);
            }
        }
    }

    private void onRemoveChannel(UUID channelUuid) {
        Channel channel = dataManager.getChannel(channelUuid);
        channel.getUsers().remove(session.getConnectedUser());
        dataManager.sendChannel(channel);
    }

    private void onCreateChannel(String channelName) {
        Channel channel = new Channel(session.getConnectedUser(), channelName);
        channel.getUsers().add(session.getConnectedUser());
        dataManager.sendChannel(channel);
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
        this.loadChannels(session.getConnectedUser());
    }

    @Override
    public void notifyChannelDeleted(Channel deletedChannel) {
        this.loadChannels(session.getConnectedUser());
    }

    @Override
    public void notifyChannelModified(Channel modifiedChannel) {
        this.loadChannels(session.getConnectedUser());
    }
}
