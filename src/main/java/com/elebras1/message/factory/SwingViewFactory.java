package com.elebras1.message.factory;

import com.elebras1.message.ihm.view.*;
import com.elebras1.message.ihm.view.callback.OnClickUuidCallback;
import com.elebras1.message.ihm.view.swing.*;

import java.util.UUID;

public class SwingViewFactory implements ViewFactory {

    @Override
    public IChannelView createChannelView(UUID id, String channelName, boolean isCreator, boolean isPrivate,
                                          OnClickUuidCallback onRemove, OnClickUuidCallback onAddMember) {
        return new ChannelView(id, channelName, isCreator, isPrivate, onRemove, onAddMember);
    }

    @Override
    public IMessageView createMessageView(UUID id, String content, String metadata, boolean isMine) {
        return new MessageView(id, content, metadata, isMine);
    }

    @Override
    public IUserView createUserView(UUID id, String displayName, boolean online) {
        return new UserView(id, displayName, online);
    }

    @Override
    public IMessagesView createMessagesView() {
        return new MessagesView();
    }

    @Override
    public IUsersView createUsersView() {
        return new UsersView();
    }

    @Override
    public IChannelsView createChannelsView() {
        return new ChannelsView();
    }

    @Override
    public IChatView createChatView() {
        return new ChatView();
    }
}
