package com.elebras1.message.controller;

import com.elebras1.message.core.DataManager;
import com.elebras1.message.datamodel.Channel;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.BubbleTextIdentifyView;
import com.elebras1.message.ihm.view.ListElementView;

import java.util.ArrayList;
import java.util.List;

public class ChannelsController implements IChannelsController, IChatObservable {
    private final DataManager dataManager;
    private final ListElementView view;
    private final List<IChatObserver> observers = new ArrayList<>();

    public ChannelsController(DataManager dataManager, ListElementView view) {
        this.dataManager = dataManager;
        this.view = view;
    }

    @Override
    public void addChatObserver(IChatObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeChatObserver(IChatObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyRecipientSelected(java.util.UUID recipientUuid) {
        for (IChatObserver observer : observers) {
            observer.onRecipientSelected(recipientUuid);
        }
    }

    @Override
    public void loadChannels(User connectedUser) {
        List<Channel> allChannels = new ArrayList<>(dataManager.getChannels());
        for (Channel channel : allChannels) {
            if (channel.getUsers().contains(connectedUser)) {
                BubbleTextIdentifyView bubble = new BubbleTextIdentifyView(channel.getUuid(), channel.getName());
                bubble.addOnClickListener(this::notifyRecipientSelected);
                view.addContent(bubble);
            }
        }
    }
}
