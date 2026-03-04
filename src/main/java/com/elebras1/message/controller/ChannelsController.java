package com.elebras1.message.controller;

import com.elebras1.message.core.DataManager;
import com.elebras1.message.datamodel.Channel;
import com.elebras1.message.datamodel.User;
import com.elebras1.message.ihm.view.BubbleTextIdentifyView;
import com.elebras1.message.ihm.view.ListBubbleTextView;

import java.util.ArrayList;
import java.util.List;

public class ChannelsController implements IChannelsController {
    private final DataManager dataManager;
    private final ListBubbleTextView view;
    private final MessagesController messagesController;

    public ChannelsController(DataManager dataManager, ListBubbleTextView view, MessagesController messagesController) {
        this.dataManager = dataManager;
        this.view = view;
        this.messagesController = messagesController;
    }

    @Override
    public void loadChannels(User connectedUser) {
        List<Channel> allChannels = new ArrayList<>(dataManager.getChannels());
        for (Channel channel : allChannels) {
            BubbleTextIdentifyView bubble = new BubbleTextIdentifyView(channel.getUuid(), channel.getName());
            bubble.addOnClickListener(messagesController::loadMessagesByRecipientUuid);
            view.addContent(bubble);
        }
    }

}
